package com.finance.servlet;

import com.finance.dao.FinanceDAO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteTransactionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        new FinanceDAO().deleteTransaction(id);
        response.sendRedirect("dashboard");
    }
}
