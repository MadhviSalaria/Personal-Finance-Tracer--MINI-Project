package com.finance.servlet;

import com.finance.dao.FinanceDAO;
import com.finance.model.Transaction;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AddTransactionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Transaction t = new Transaction();
        t.setType(request.getParameter("type"));
        t.setCategory(request.getParameter("category"));
        t.setAmount(Double.parseDouble(request.getParameter("amount")));
        t.setDate(request.getParameter("date"));
        t.setNote(request.getParameter("note"));

        new FinanceDAO().addTransaction(t);
        response.sendRedirect("dashboard");
    }
}
