package com.android.kotlin.personaltrainer.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.android.kotlin.personaltrainer.R;
import com.itextpdf.layout.properties.VerticalAlignment;

public class TemplatePdf {

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;

    public TemplatePdf(Context context) {
        this.context = context;
    }

    public void openDocument(String nombrePdf) {
        createFile(nombrePdf);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
            pdfWriter = new PdfWriter(fileOutputStream);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            document = new Document(pdfDocument, PageSize.A4);
        } catch (Exception e) {
            Log.e("openDocument", e.toString());
        }
    }

    public void closeDocument() {
        document.close();
    }

    private void createFile(String nombrePdf) {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File folder = new File(pdfPath, "Personal_Trainer_PDF");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String nombreArchivo = nombrePdf + ".pdf";
        pdfFile = new File(folder, nombreArchivo);
    }

    public void addHeaders(String nombrePlan, String nombreCliente, String fechaInicio, String fechaFin, String tipo) {
        paragraph = new Paragraph("PERSONAL TRAINER")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(20f)
                .setBold();
        document.add(paragraph);

        paragraph = new Paragraph()
                .setTextAlignment(TextAlignment.LEFT)
                .add(new Text("Plan de entrenamiento: ").setBold())
                .add(new Text(nombrePlan))
                .add("\n")
                .add(new Text("Cliente: ").setBold())
                .add(new Text(nombreCliente))
                .add("\n")
                .add(new Text("Fecha de inicio: ").setBold())
                .add(new Text(fechaInicio))
                .add("\n")
                .add(new Text("Fecha de finalización: ").setBold())
                .add(new Text(fechaFin))
                .add("\n")
                .add(new Text("Tipo de plan: ").setBold())
                .add(new Text(tipo))
                .add("\n\n");
        document.add(paragraph);
    }


    public void addParagraph(String text) {
        paragraph = new Paragraph(text);
        paragraph.add("\n");
        document.add(paragraph);
    }

    public void addTextInParagraph(String text) {
        paragraph = new Paragraph();
        Text text1 = new Text(text).setBold();
        paragraph.add(text1);
        document.add(paragraph);
    }

    public void createTable(String[] header, ArrayList<String[]> body) {
        paragraph = new Paragraph();

        float[] columnWidths = new float[header.length];
        Table table = new Table(columnWidths);
        table.setWidth(UnitValue.createPercentValue(100f));
        Cell cell;

        int indexC = 0;
        while (indexC < header.length) {
            cell = new Cell().add(new Paragraph(header[indexC]));
            cell.setTextAlignment(TextAlignment.CENTER);;
            cell.setBackgroundColor(ColorConstants.BLACK);
            cell.setFontColor(ColorConstants.WHITE);
            table.addCell(cell);
            indexC++;
        }

        for (int indexR = 0; indexR < body.size(); indexR++) {
            String[] row = body.get(indexR);
            for (indexC = 0; indexC < header.length; indexC++) {

                if (indexC == 0) {
                    Image image = convertirImagen();
                    cell = new Cell().add(image);
                } else if (indexC == header.length - 1) {
                    // Crear un enlace clicable para la URL
                    Link link = new Link("Ver video", PdfAction.createURI(row[indexC]));
                    link.setFontColor(ColorConstants.BLUE);
                    link.setUnderline();
                    cell = new Cell().add(new Paragraph(link));
                } else {
                    // Para las otras columnas, solo agregamos el texto
                    cell = new Cell().add(new Paragraph(row[indexC]));
                }

                cell.setTextAlignment(TextAlignment.CENTER);
                cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                table.addCell(cell);
            }
        }

        paragraph.add(table);
        document.add(paragraph);
    }

    private Image convertirImagen() {
        Drawable drawable = context.getDrawable(R.drawable.logo);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();
        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);
        image.setHeight(50);
        return image;
    }

}
