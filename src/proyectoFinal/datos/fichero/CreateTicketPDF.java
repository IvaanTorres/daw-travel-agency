/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoFinal.datos.fichero;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import proyectoFinal.datos.bda.usuarioDAO;
import proyectoFinal.modelo.DetallePack;
import proyectoFinal.modelo.Pack;
import java.sql.SQLException;
import proyectoFinal.modelo.Herramientas;

/**
 *
 * @author itoga
 */
public class CreateTicketPDF {
    
    private Herramientas herramientas = new Herramientas();

    private static int ID = 1;
    private Pack pack;
    private usuarioDAO ticketDAO;
    private File file;
    
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/YY");
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    public File getFile() {
        return file;
    }
    
    public usuarioDAO getTicketDAO() {
        return ticketDAO;
    }

    public void setTicketDAO(usuarioDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public CreateTicketPDF() {
    }

    public String getTicketName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH_mm");
        String formattedDateTime = LocalTime.now().format(formatter);
        String idRequest = String.format("%02d", ID);
        String fileFormat = ".pdf";

        String ticketName = "Pack" + idRequest + "_" + formattedDateTime + fileFormat;
        return ticketName;
    }

    public void createTicketPDF(Pack pack) throws FileNotFoundException, DocumentException {
        this.pack = pack;
        Path ruta = Path.of("src\\proyectoFinal\\tickets" + "\\" + getTicketName());

        Document document = new Document();
        FileOutputStream pdfFile = new FileOutputStream(ruta.toString());
        PdfWriter.getInstance(document, pdfFile);
        document.open();
        
        createLogoPDF(document);
        createTitlePDF(document);
        createInfoParagraph(document);
        createTables(document);
        file = ruta.toFile();
        document.close();
        ID++;
    }

    public void createLogoPDF(Document document) {
        try {
            Image imagen = Image.getInstance("src/proyectoFinal/img/iconoApp.png");
            imagen.scaleAbsolute(100, 100);
            imagen.setAlignment(Element.ALIGN_RIGHT);
            imagen.setAbsolutePosition(450, 730);
            document.add(imagen);
        } catch (DocumentException | IOException e) {
            herramientas.showAlertErr("Error en imagen: " + e.getMessage());
        }
    }

    public void createTitlePDF(Document document) {
        try {
            Paragraph titulo = new Paragraph("Ticket del pack nº" + ID + "\n",
                    FontFactory.getFont("arial", 25, Font.BOLD, BaseColor.BLUE));
            Paragraph desc = new Paragraph(pack.getDescripcion() + "\n\n",
                    FontFactory.getFont("arial", 15, Font.BOLD, BaseColor.BLACK));
            document.add(titulo);
            document.add(desc);
        } catch (DocumentException e) {
            herramientas.showAlertErr("Error en titulo: " + e.getMessage());
        }
    }

    public void createInfoParagraph(Document document) {  
        try {
            Paragraph paragrapthInfo = new Paragraph();
            paragrapthInfo.add("\n\nPedido número: " + ID + "\n");
            paragrapthInfo.add("Num. referencia: REF-" + pack.getIdPack() + "\n");
            paragrapthInfo.add("Pedido realizado por: " + ticketDAO.nombreUsuario(pack) + "\n");
            paragrapthInfo.add("Fecha del pedido: " + dateFormat.format(LocalDate.now()) + " a las " + timeFormat.format(LocalTime.now()) + "h\n");
            paragrapthInfo.add("Fecha de inicio del pack: " + dateFormat.format(pack.getFechaInicio()) + "\n");
            paragrapthInfo.add("Fecha de finalizacion del pack: " + dateFormat.format(pack.getFechaFinal()) + "\n");
            paragrapthInfo.add("PRECIO TOTAL DEL PEDIDO: " + pack.getPrecio() + "€\n\n\n");
            paragrapthInfo.setAlignment(Element.ALIGN_RIGHT);

            document.add(paragrapthInfo);
        } catch (DocumentException e) {
            herramientas.showAlertErr("Error en info ticket: " + e.getMessage());
        } catch (SQLException e){
            herramientas.showAlertErr("Error: " + e.getMessage());
        }
    }

    public void createTables(Document document) {
        try {
            PdfPTable table = new PdfPTable(6);
            table.addCell("ACT ID");
            table.addCell("NOMBRE");
            table.addCell("Nº PLAZAS");
            table.addCell("FECHA INICIO");
            table.addCell("FECHA FIN");
            table.addCell("PRECIO");

            for (DetallePack act : pack.getListaActividades()) {
                table.addCell(String.valueOf(act.getIdActividad()));
                table.addCell(act.getNombreDetallePack());
                table.addCell(String.valueOf(act.getNumPlazas()));
                table.addCell(String.valueOf(dateFormat.format(act.getFechaInicio())));
                table.addCell(String.valueOf(dateFormat.format(act.getFechaFinal())));
                table.addCell(String.valueOf(act.getPrecio()));
            }
            
            document.add(table);
        } catch (DocumentException e) {
            herramientas.showAlertErr("Error en carga de tablas: " + e.getMessage());
        }
    }
}
