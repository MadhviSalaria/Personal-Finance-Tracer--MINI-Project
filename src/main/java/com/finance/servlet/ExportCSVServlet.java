package com.finance.servlet;

import com.finance.dao.FinanceDAO;
import com.finance.model.Transaction;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class ExportCSVServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=Finance_Report.csv");

        FinanceDAO dao = new FinanceDAO();
        List<Transaction> transactions = dao.getAllTransactions();

        try (PrintWriter writer = response.getWriter()) {
            writer.println("ID,Type,Category,Amount,Date,Note");
            for (Transaction t : transactions) {
                writer.printf("%d,%s,%s,%.2f,%s,%s%n",
                        t.getId(), t.getType(), t.getCategory(),
                        t.getAmount(), t.getDate(), t.getNote().replace(",", " "));
            }
        }
    }
}
