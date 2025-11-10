package com.finance.servlet;

import com.finance.dao.FinanceDAO;
import com.finance.model.Transaction;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Date;

/**
 * Servlet to export all transactions as a PDF report.
 * Requires iTextPDF JAR in WEB-INF/lib (itextpdf-5.5.13.3.jar)
 */
public class ExportPDFServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response type and file header
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Finance_Report.pdf");

        // Get all transactions from the database
        FinanceDAO dao = new FinanceDAO();
        List<Transaction> transactions = dao.getAllTransactions();

        // Create PDF
        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Paragraph title = new Paragraph("Personal Finance Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Subtitle
            Font subFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Generated on: " + new Date().toString(), subFont));
            document.add(Chunk.NEWLINE);

            // Table setup
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{5, 15, 20, 15, 15, 30});

            // Table header styling
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            BaseColor headerColor = new BaseColor(0, 102, 204);

            String[] headers = {"ID", "Type", "Category", "Amount", "Date", "Note"};
            for (String h : headers) {
                PdfPCell header = new PdfPCell(new Phrase(h, headFont));
                header.setBackgroundColor(headerColor);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setPadding(6);
                table.addCell(header);
            }

            // Table data rows
            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            for (Transaction t : transactions) {
                table.addCell(new Phrase(String.valueOf(t.getId()), dataFont));
                table.addCell(new Phrase(t.getType(), dataFont));
                table.addCell(new Phrase(t.getCategory(), dataFont));
                table.addCell(new Phrase(String.format("₹%.2f", t.getAmount()), dataFont));
                table.addCell(new Phrase(t.getDate(), dataFont));
                table.addCell(new Phrase(t.getNote(), dataFont));
            }

            // Add table to document
            document.add(table);
            document.add(Chunk.NEWLINE);

            // Summary
            double totalIncome = dao.getTotalIncome();
            double totalExpense = dao.getTotalExpense();
            double savings = totalIncome - totalExpense;

            Paragraph summary = new Paragraph(String.format(
                    "Total Income: ₹%.2f   |   Total Expenses: ₹%.2f   |   Savings: ₹%.2f",
                    totalIncome, totalExpense, savings
            ), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            summary.setAlignment(Element.ALIGN_CENTER);
            document.add(summary);

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
