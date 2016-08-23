package me.quydo.androidkeytool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.util.Enumeration;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.xml.bind.DatatypeConverter;

import me.quydo.androidkeytool.util.HashUtils;
import me.quydo.androidkeytool.util.StringUtils;

/**
 * 
 * @author quydm
 *
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel container;
	private JPanel messagePanel;
	private JPanel form;

	private JTextField txtKeyStorePath;
	private JTextField txtKeyStorePass;
	private JTextField txtMD5;
	private JTextField txtSHA1;
	private JTextField txtFBKeyhash;

	private JComboBox aliasList;

	private JLabel txtInfoMessage;
	private JLabel txtErroMessage;

	private JButton btnCopyMD5;
	private JButton btnCopySHA1;
	private JButton btnCopyFBKeyhash;

	private KeyStore currentKS;

	public Main() {
		super("Android Keytool v1.0");
		setupMessagePanel();
		setupForm();
		setupContainer();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void setupContainer() {
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(messagePanel);
		container.add(form);
		add(container);
	}

	private void setupMessagePanel() {
		messagePanel = new JPanel(new GridBagLayout());
		messagePanel.setPreferredSize(new Dimension(200, 40));

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(20, 10, 10, 10);

		txtInfoMessage = new JLabel("Text has been copied to clipboard");
		txtInfoMessage.setForeground(Color.decode("#31708f"));
		txtInfoMessage.setVisible(false);
		messagePanel.add(txtInfoMessage, constraints);

		txtErroMessage = new JLabel();
		txtErroMessage.setForeground(Color.decode("#a94442"));
		txtErroMessage.setVisible(false);
		messagePanel.add(txtErroMessage, constraints);
	}

	private void setupForm() {
		form = new JPanel(new GridBagLayout());

		// first line: label + text field + button
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridy = 0;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		JLabel lblKeyStorePath = new JLabel("Key store path");
		form.add(lblKeyStorePath, constraints);

		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(10, 0, 10, 0);
		txtKeyStorePath = new JTextField(40);
		txtKeyStorePath.setEditable(false);
		form.add(txtKeyStorePath, constraints);

		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(10, 0, 10, 10);
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					txtKeyStorePath.setText(selectedFile.getAbsolutePath());
					txtErroMessage.setVisible(false);
				}
			}
		});
		form.add(btnBrowse, constraints);

		// second line: label + text field
		constraints.gridy = 1;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		JLabel lblKeyStorePassword = new JLabel("Key store password");
		form.add(lblKeyStorePassword, constraints);

		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(10, 0, 10, 0);
		txtKeyStorePass = new JTextField(40);
		form.add(txtKeyStorePass, constraints);

		// third line: label + combo box + button
		constraints.gridy = 2;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		JLabel lblKeyStoreAlias = new JLabel("List key alias");
		form.add(lblKeyStoreAlias, constraints);

		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(10, 0, 10, 0);
		aliasList = new JComboBox();
		aliasList.setEnabled(false);
		aliasList.addItem("--Select an alias--");
		aliasList.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (aliasList.getSelectedIndex() < 1)
					return;

				try {
					Certificate certificate = currentKS.getCertificate(aliasList.getSelectedItem().toString());

					MessageDigest md = MessageDigest.getInstance("SHA");
					md.update(certificate.getEncoded());

					String fbKeyhash = DatatypeConverter.printBase64Binary(md.digest());
					String md5 = HashUtils.md5(certificate.getEncoded());
					String sha1 = HashUtils.sha1(certificate.getEncoded());

					String[] splitString = (md5.split("[a-z0-9]{2}"));
					for (String item : splitString) {
						System.out.println(item);
					}

					txtFBKeyhash.setText(fbKeyhash);
					txtFBKeyhash.setEnabled(true);
					txtMD5.setText(StringUtils.addColonAndUpperCase(md5));
					txtMD5.setEnabled(true);
					txtSHA1.setText(StringUtils.addColonAndUpperCase(sha1));
					txtSHA1.setEnabled(true);
					btnCopyMD5.setEnabled(true);
					btnCopySHA1.setEnabled(true);
					btnCopyFBKeyhash.setEnabled(true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		form.add(aliasList, constraints);

		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(10, 0, 10, 10);
		JButton btnLoadAlias = new JButton("Load alias");
		btnLoadAlias.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtKeyStorePath.getText().isEmpty()) {
					txtErroMessage.setText("Enter path to keystore");
					txtErroMessage.setVisible(true);
					return;
				}

				if (txtKeyStorePass.getText().isEmpty()) {
					txtErroMessage.setText("Enter keystore password");
					txtErroMessage.setVisible(true);
					return;
				}

				loadAlias();
			}
		});
		form.add(btnLoadAlias, constraints);

		// fourth line: label + text field + button
		constraints.gridy = 3;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		JLabel lblMD5 = new JLabel("MD5");
		form.add(lblMD5, constraints);

		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(10, 0, 10, 0);
		txtMD5 = new JTextField(40);
		txtMD5.setEditable(false);
		txtMD5.setEnabled(false);
		form.add(txtMD5, constraints);

		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(10, 0, 10, 10);
		btnCopyMD5 = new JButton("Copy");
		btnCopyMD5.setEnabled(false);
		btnCopyMD5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				copyToClipboard(txtMD5.getText());
			}
		});
		form.add(btnCopyMD5, constraints);

		// fifth line: label + text field + button
		constraints.gridy = 4;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		JLabel lblSHA1 = new JLabel("SHA1");
		form.add(lblSHA1, constraints);

		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(10, 0, 10, 0);
		txtSHA1 = new JTextField(40);
		txtSHA1.setEditable(false);
		txtSHA1.setEnabled(false);
		form.add(txtSHA1, constraints);

		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(10, 0, 10, 10);
		btnCopySHA1 = new JButton("Copy");
		btnCopySHA1.setEnabled(false);
		btnCopySHA1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				copyToClipboard(txtSHA1.getText());
			}
		});
		form.add(btnCopySHA1, constraints);

		// sixth line: label + text field + button
		constraints.gridy = 5;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);
		JLabel lblFBKeyhash = new JLabel("Facebook Key hash");
		form.add(lblFBKeyhash, constraints);

		constraints.gridx = 1;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.insets = new Insets(10, 0, 10, 0);
		txtFBKeyhash = new JTextField(40);
		txtFBKeyhash.setEditable(false);
		txtFBKeyhash.setEnabled(false);
		form.add(txtFBKeyhash, constraints);

		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(10, 0, 10, 10);
		btnCopyFBKeyhash = new JButton("Copy");
		btnCopyFBKeyhash.setEnabled(false);
		btnCopyFBKeyhash.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				copyToClipboard(txtFBKeyhash.getText());
			}
		});
		form.add(btnCopyFBKeyhash, constraints);

		// seventh line: label
		constraints.gridy = 6;
		constraints.gridx = 2;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(10, 10, 20, 10);
		JLabel lblAuthor = new JLabel("author: quydm");
		form.add(lblAuthor, constraints);
	}

	private void copyToClipboard(String strToCopy) {
		StringSelection selection = new StringSelection(strToCopy);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(selection, selection);

		txtInfoMessage.setVisible(true);
		Timer timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				txtInfoMessage.setVisible(false);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	private void loadAlias() {
		aliasList.removeAllItems();
		aliasList.addItem("--Select an alias--");

		try {
			File file = new File(txtKeyStorePath.getText());
			FileInputStream is = new FileInputStream(file);

			currentKS = KeyStore.getInstance(KeyStore.getDefaultType());
			currentKS.load(is, txtKeyStorePass.getText().toCharArray());

			Enumeration<String> enumeration = currentKS.aliases();
			while (enumeration.hasMoreElements())
				aliasList.addItem(enumeration.nextElement());
			aliasList.setEnabled(true);
			is.close();
			txtErroMessage.setVisible(false);
		} catch (Exception e) {
			e.printStackTrace();
			txtErroMessage.setText("Keystore was tampered with, or password was incorrect");
			txtErroMessage.setVisible(true);
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Main().setVisible(true);
			}
		});
	}

}
