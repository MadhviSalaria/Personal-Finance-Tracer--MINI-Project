<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Add Transaction</title>
  <style>
    body{font-family:"Segoe UI",Tahoma,Verdana,sans-serif;background:linear-gradient(135deg,#e3f2fd,#bbdefb);margin:0;height:100vh;display:flex;align-items:center;justify-content:center;}
    .container{background:#fff;border-radius:12px;padding:40px 50px;width:420px;box-shadow:0 6px 18px rgba(0,0,0,.1)}
    h2{text-align:center;margin-bottom:20px;color:#1e3a8a}
    label{display:block;margin-top:15px;color:#333;font-weight:500}
    input,select{width:100%;padding:10px;margin-top:6px;border:1px solid #ccc;border-radius:6px;font-size:14px}
    input:focus,select:focus{border-color:#1e88e5;box-shadow:0 0 5px rgba(30,136,229,.4);outline:none}
    .row{display:flex;gap:10px}
    .row > div{flex:1}
    .actions{margin-top:24px;display:flex;gap:10px}
    .btn{flex:1;text-align:center;background:#1e88e5;color:#fff;padding:12px;border-radius:6px;text-decoration:none;border:none;cursor:pointer;font-weight:600}
    .btn:hover{background:#1565c0}
    .link{display:block;margin-top:14px;text-align:center;color:#1e88e5;text-decoration:none}
    .link:hover{color:#0d47a1}
  </style>
</head>
<body>
  <div class="container">
    <h2>Add Transaction</h2>
    <form action="addTransaction" method="post">
      <div class="row">
        <div>
          <label>Type</label>
          <select name="type">
            <option>Income</option>
            <option>Expense</option>
          </select>
        </div>
        <div>
          <label>Date</label>
          <input type="date" name="date" required>
        </div>
      </div>

      <label>Category</label>
      <input type="text" name="category" required>

      <div class="row">
        <div>
          <label>Amount</label>
          <input type="number" step="0.01" name="amount" required>
        </div>
        <div>
          <label>Note</label>
          <input type="text" name="note" placeholder="optional">
        </div>
      </div>

      <!-- preserve selected month if coming from dashboard -->
      <input type="hidden" name="month" value="<%= request.getParameter("month")!=null?request.getParameter("month"):"" %>">

      <div class="actions">
        <button type="submit" class="btn">Save</button>
        <a class="btn" href="dashboard<%= request.getParameter("month")!=null?("?month="+request.getParameter("month")):"" %>">Cancel</a>
      </div>
    </form>
    <a class="link" href="dashboard">Go to Dashboard</a>
  </div>
</body>
</html>
