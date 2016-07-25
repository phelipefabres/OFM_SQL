/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2014 TIBCO Software Inc. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.jasperreports.view;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.InputStream;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JasperReportsContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class can be used at design time to preview the report
 * templates. It was included in the main library as a development tool in order to make up
 * for the missing visual designer.
 * <p>
 * The class is a simple
 * Swing-based Java application that can load and display a report template either in its raw
 * JRXML form or in its compiled form. Even though it is not a complex GUI application and
 * lacks advanced functionality like dragging and dropping visual report elements, it is a
 * very helpful tool. All the supplied JasperReports samples were initially created using this design
 * viewer.
 * </p><p>
 * All the supplied samples already have Ant tasks in their <code>build.xml</code> files that will launch
 * this design viewer to display the report templates.
 * There are two Ant tasks for each sample report: <code>viewDesign</code> and <code>viewDesignXML</code>. The
 * first one loads the compiled report template that is normally found in the <code>*.jasper</code> file.
 * The second one loads the JRXML report template, which is more useful since you can
 * edit the JRXML file and click the Reload button to immediately see the modification on
 * the screen.
 * </p>
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 */
public class JasperDesignViewer extends javax.swing.JFrame 
{
	private static final Log log = LogFactory.getLog(JasperDesignViewer.class);

	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;
	
	/**
	 * @see #JasperDesignViewer(JasperReportsContext, String, boolean)
	 */
	public JasperDesignViewer(String sourceFile, boolean isXML)  throws JRException
	{
		this(DefaultJasperReportsContext.getInstance(), sourceFile, isXML);
	}
	
	/**
	 * @see #JasperDesignViewer(JasperReportsContext, InputStream, boolean)
	 */
	public JasperDesignViewer(InputStream is, boolean isXML) throws JRException
	{
		this(DefaultJasperReportsContext.getInstance(), is, isXML);
	}
	
	/**
	 * @see #JasperDesignViewer(JasperReportsContext, JRReport)
	 */
	public JasperDesignViewer(JRReport report) throws JRException
	{
		this(DefaultJasperReportsContext.getInstance(), report);
	}
	
	/**
	 * 
	 */
	public JasperDesignViewer(
		JasperReportsContext jasperReportsContext,
		String sourceFile, 
		boolean isXML
		)  throws JRException
	{
		initComponents();

		JRDesignViewer viewer = new JRDesignViewer(jasperReportsContext, sourceFile, isXML);
		this.pnlMain.add(viewer, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 */
	public JasperDesignViewer(
		JasperReportsContext jasperReportsContext,
		InputStream is, 
		boolean isXML
		) throws JRException
	{
		initComponents();

		JRDesignViewer viewer = new JRDesignViewer(jasperReportsContext, is, isXML);
		this.pnlMain.add(viewer, BorderLayout.CENTER);
	}
	
	/**
	 * 
	 */
	public JasperDesignViewer(
		JasperReportsContext jasperReportsContext,
		JRReport report
		) throws JRException
	{
		initComponents();

		JRDesignViewer viewer = new JRDesignViewer(jasperReportsContext, report);
		this.pnlMain.add(viewer, BorderLayout.CENTER);
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents() {//GEN-BEGIN:initComponents
		pnlMain = new javax.swing.JPanel();

		setTitle("JasperDesignViewer");
		setIconImage(new javax.swing.ImageIcon(getClass().getResource("/net/sf/jasperreports/view/images/jricon.GIF")).getImage());
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				exitForm();
			}
		});

		pnlMain.setLayout(new java.awt.BorderLayout());

		getContentPane().add(pnlMain, java.awt.BorderLayout.CENTER);

		pack();
		
		Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
		java.awt.Dimension screenSize = toolkit.getScreenSize();
		int screenResolution = toolkit.getScreenResolution();
		float zoom = ((float) screenResolution) / JRViewer.REPORT_RESOLUTION;
		
		int height = (int) (550 * zoom);
		if (height > screenSize.getHeight())
		{
			height = (int) screenSize.getHeight();
		}		
		int width = (int) (750 * zoom);
		if (width > screenSize.getWidth())
		{
			width = (int) screenSize.getWidth();
		}
		
		java.awt.Dimension dimension = new java.awt.Dimension(width, height);
		setSize(dimension);
		setLocation((screenSize.width-width)/2,(screenSize.height-height)/2);
	}//GEN-END:initComponents

	/** Exit the Application */
	void exitForm() {//GEN-FIRST:event_exitForm
		System.exit(0);
	}//GEN-LAST:event_exitForm
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) 
	{
		String fileName = null;
		boolean isXMLFile = false;

		for (int i = 0; i < args.length; i++)
		{
			if ( args[i].startsWith("-XML") )
			{
				isXMLFile = true;
			}
			else if ( args[i].startsWith("-F") )
			{
				fileName = args[i].substring(2);
			}
			else
			{
				fileName = args[i];
			}
		}
		
		if(fileName == null)
		{
			usage();
			return;
		}

		if (!isXMLFile && fileName.endsWith(".jrxml"))
		{
			isXMLFile = true;
		}

		try
		{
			viewReportDesign(fileName, isXMLFile);
		}
		catch (JRException e)
		{
			if (log.isErrorEnabled())
			{
				log.error("Error viewing report design.", e);
			}
			System.exit(1);
		}
	}
	
	/**
	 *
	 */
	private static void usage()
	{
		System.out.println( "JasperDesignViewer usage:" );
		System.out.println( "\tjava JasperDesignViewer [-XML] file" );
	}
	
	/**
	 * @see #viewReportDesign(JasperReportsContext, String, boolean)
	 */
	public static void viewReportDesign(String sourceFile, boolean isXML) throws JRException
	{
		viewReportDesign(DefaultJasperReportsContext.getInstance(), sourceFile, isXML);
	}
	
	/**
	 * @see #viewReportDesign(JasperReportsContext, InputStream, boolean)
	 */
	public static void viewReportDesign(InputStream is, boolean isXML) throws JRException
	{
		viewReportDesign(DefaultJasperReportsContext.getInstance(), is, isXML);
	}
	
	/**
	 * @see #viewReportDesign(JasperReportsContext, JRReport)
	 */
	public static void viewReportDesign(JRReport report) throws JRException
	{
		viewReportDesign(DefaultJasperReportsContext.getInstance(), report);
	}
	
	/**
	 *
	 */
	public static void viewReportDesign(
		JasperReportsContext jasperReportsContext,
		String sourceFile, 
		boolean isXML
		) throws JRException
	{
		JasperDesignViewer jasperDesignViewer = new JasperDesignViewer(jasperReportsContext, sourceFile, isXML);
		jasperDesignViewer.setVisible(true);
	}
	
	/**
	 *
	 */
	public static void viewReportDesign(
		JasperReportsContext jasperReportsContext,
		InputStream is, 
		boolean isXML
		) throws JRException
	{
		JasperDesignViewer jasperDesignViewer = new JasperDesignViewer(jasperReportsContext, is, isXML);
		jasperDesignViewer.setVisible(true);
	}
	
	/**
	 *
	 */
	public static void viewReportDesign(
		JasperReportsContext jasperReportsContext,
		JRReport report
		) throws JRException
	{
		JasperDesignViewer jasperDesignViewer = new JasperDesignViewer(jasperReportsContext, report);
		jasperDesignViewer.setVisible(true);
	}
	
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel pnlMain;
	// End of variables declaration//GEN-END:variables
}
