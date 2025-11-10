<%@ page import="java.util.*,com.finance.model.Transaction" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Finance Dashboard</title>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <style>
    body {
      font-family: "Segoe UI", Tahoma, Verdana, sans-serif;
      background: linear-gradient(135deg, #e3f2fd, #bbdefb);
      color: #333;
      margin: 0;
    }
    header {
      background: #0d47a1;
      color: white;
      padding: 18px;
      text-align: center;
      font-size: 22px;
      font-weight: 600;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }
    .wrap { max-width: 1100px; margin: 0 auto; padding: 20px; }
    .toolbar { display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 10px; margin: 20px 0; }
    .btn {
      background: #1e88e5; color: white; border: none; border-radius: 6px;
      padding: 9px 14px; text-decoration: none; font-weight: 600; cursor: pointer;
      transition: 0.2s;
    }
    .btn:hover { background: #1565c0; }
    .summary { display: flex; justify-content: center; gap: 20px; flex-wrap: wrap; }
    .card {
      background: white; border-radius: 10px; padding: 18px 28px; width: 220px;
      box-shadow: 0 6px 12px rgba(0,0,0,0.1); text-align: center;
    }
    .card h3 { color: #1565c0; margin: 0 0 8px; }
    .card h2 { margin: 0; }
    .text-danger { color: #d32f2f; }
    .text-success { color: #388e3c; }
    .grid {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 20px;
      margin: 25px 0;
    }
    .panel {
      background: white; border-radius: 10px; padding: 16px;
      box-shadow: 0 6px 12px rgba(0,0,0,0.1);
    }
    table {
      width: 100%; border-collapse: collapse; background: white; border-radius: 10px;
      overflow: hidden; box-shadow: 0 6px 12px rgba(0,0,0,0.1);
    }
    th, td { padding: 12px; border-bottom: 1px solid #eee; text-align: center; }
    th { background: #1976d2; color: white; }
    tr:hover { background: #f5f9ff; }
    footer {
      background: #0d47a1; color: white; text-align: center; padding: 12px;
      margin-top: 30px; font-size: 14px;
    }
  </style>
</head>
<body>
  <header>Personal Finance Dashboard</header>
  <div class="wrap">

    <!-- Toolbar -->
    <div class="toolbar">
      <div>
        <a class="btn" href="addTransaction.jsp">Add Transaction</a>
        <a class="btn" href="exportCSV">Download CSV</a>
        <a class="btn" href="exportPDF">Download PDF</a>
      </div>
    </div>

    <!-- Summary Cards -->
    <div class="summary">
      <div class="card">
        <h3>Total Income</h3>
        <h2>₹<%= String.format("%.2f",(Double)request.getAttribute("income")) %></h2>
      </div>
      <div class="card">
        <h3>Total Expenses</h3>
        <h2 class="text-danger">₹<%= String.format("%.2f",(Double)request.getAttribute("expense")) %></h2>
      </div>
      <div class="card">
        <h3>Savings</h3>
        <h2 class="<%= ((Double)request.getAttribute("savings"))>=0 ? "text-success":"text-danger" %>">
          ₹<%= String.format("%.2f",(Double)request.getAttribute("savings")) %>
        </h2>
      </div>
    </div>

    <!-- Charts -->
    <div class="grid">
      <div class="panel">
        <h3 style="margin:6px 0 12px;color:#1565c0">Expense Breakdown (Pie)</h3>
        <canvas id="pie"></canvas>
      </div>
      <div class="panel">
        <h3 style="margin:6px 0 12px;color:#1565c0">Expenses by Category (Bar)</h3>
        <canvas id="bar"></canvas>
      </div>
    </div>

    <%
      Map<String, Double> byCategory = (Map<String, Double>) request.getAttribute("byCategory");
      String labelsJson = "[]";
      String dataJson = "[]";
      if (byCategory != null && !byCategory.isEmpty()) {
          labelsJson = new com.google.gson.Gson().toJson(byCategory.keySet());
          dataJson = new com.google.gson.Gson().toJson(byCategory.values());
      }
    %>

    <script>
      const labels = <%= labelsJson %>;
      const data = <%= dataJson %>;

      new Chart(document.getElementById('pie'), {
        type: 'pie',
        data: {
          labels,
          datasets: [{
            data,
            backgroundColor: ['#42a5f5','#ef5350','#ffb300','#66bb6a','#ab47bc','#26a69a']
          }]
        }
      });

      new Chart(document.getElementById('bar'), {
        type: 'bar',
        data: {
          labels,
          datasets: [{
            label: 'Expenses by Category',
            data,
            backgroundColor: '#1976d2'
          }]
        },
        options: {
          scales: { y: { beginAtZero: true } }
        }
      });
    </script>

    <!-- Transactions Table -->
    <h2 style="color:#1565c0;text-align:center;margin-top:10px;">Transaction History</h2>
    <div class="panel">
      <table>
        <tr>
          <th>ID</th>
          <th>Type</th>
          <th>Category</th>
          <th>Amount</th>
          <th>Date</th>
          <th>Note</th>
          <th>Action</th>
        </tr>
        <%
          List<Transaction> txs = (List<Transaction>) request.getAttribute("transactions");
          for (Transaction t : txs) {
        %>
        <tr>
          <td><%= t.getId() %></td>
          <td><%= t.getType() %></td>
          <td><%= t.getCategory() %></td>
          <td>₹<%= String.format("%.2f", t.getAmount()) %></td>
          <td><%= t.getDate() %></td>
          <td><%= t.getNote() %></td>
          <td>
            <a class="btn" style="background:#e53935"
               onclick="return confirm('Delete this transaction?');"
               href="deleteTransaction?id=<%= t.getId() %>">Delete</a>
          </td>
        </tr>
        <% } %>
      </table>
    </div>
  </div>

  <footer>&copy; <%= java.time.Year.now() %> | Personal Finance Tracker</footer>
</body>
</html>
