package com.finance.servlet;

import com.finance.dao.FinanceDAO;
import com.finance.model.Transaction;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        FinanceDAO dao = new FinanceDAO();

        // Fetch totals
        double totalIncome = dao.getTotalIncome();
        double totalExpense = dao.getTotalExpense();
        double savings = totalIncome - totalExpense;

        // Fetch all transactions
        List<Transaction> transactions = dao.getAllTransactions();

        // Fetch category-wise expense data
        Map<String, Double> byCategory = dao.getExpenseByCategory();

        // Set attributes to send to JSP
        request.setAttribute("income", totalIncome);
        request.setAttribute("expense", totalExpense);
        request.setAttribute("savings", savings);
        request.setAttribute("transactions", transactions);
        request.setAttribute("byCategory", byCategory);

        // Forward to dashboard.jsp
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}
