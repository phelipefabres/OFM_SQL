/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.net.multiway.ofm.report;

import com.net.multiway.ofm.daos.DataEventDAO;
import com.net.multiway.ofm.daos.OccurrenceDAO;
import java.sql.SQLException;
import javax.persistence.Persistence;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author phelipe
 */
public class Reports {

    public void occurrenceReport() throws SQLException, JRException {
        // HashMap paramRel = new HashMap();

        try {
            OccurrenceDAO dao = new OccurrenceDAO();
            JasperReport report = JasperCompileManager.compileReport("resources/occurrenceReport.jrxml");

            JRDataSource jrds = new JRBeanCollectionDataSource(dao.findOccurrenceEntities());

            JasperPrint print = JasperFillManager.fillReport("resources/occurrenceReport.jasper", null, jrds);
            JasperExportManager.exportReportToPdfFile(print, "resources/occurrenceReport.pdf");
           // JasperViewer.viewReport(print, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eventsReport() throws SQLException, JRException {

        try {
            DataEventDAO dao = new DataEventDAO();
            JasperReport report = JasperCompileManager.compileReport("resources/eventsReport.jrxml");

            JRDataSource jrds = new JRBeanCollectionDataSource(dao.findDataEventEntities());

            JasperPrint print = JasperFillManager.fillReport("resources/eventsReport.jasper", null, jrds);
            JasperExportManager.exportReportToPdfFile(print, "resources/eventsReport.pdf");
           // JasperViewer.viewReport(print, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
