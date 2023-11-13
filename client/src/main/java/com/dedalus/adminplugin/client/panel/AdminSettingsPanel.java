/*
 * Copyright 2021 Kaur Palang
 * Copyright 2023 Julian Pufler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dedalus.adminplugin.client.panel;

import com.dedalus.adminplugin.shared.Constants;
import com.dedalus.adminplugin.shared.interfaces.CustomSettingsServletInterface;
import com.dedalus.adminplugin.shared.model.CustomSetting;
import com.mirth.connect.client.core.ClientException;
import com.mirth.connect.client.core.PropertiesConfigurationUtil;
import com.mirth.connect.client.core.TaskConstants;
import com.mirth.connect.client.ui.AbstractSettingsPanel;
import com.mirth.connect.client.ui.Mirth;
import com.mirth.connect.client.ui.PlatformUI;
import com.mirth.connect.client.ui.RefreshTableModel;
import com.mirth.connect.client.ui.TextFieldCellEditor;
import com.mirth.connect.client.ui.UIConstants;
import com.mirth.connect.client.ui.components.MirthButton;
import com.mirth.connect.client.ui.components.MirthCheckBox;
import com.mirth.connect.client.ui.components.MirthDialogTableCellEditor;
import com.mirth.connect.client.ui.components.MirthPasswordField;
import com.mirth.connect.client.ui.components.MirthPasswordTableCellRenderer;
import com.mirth.connect.client.ui.components.MirthTable;
import com.mirth.connect.client.ui.components.MirthTextField;
import com.mirth.connect.util.ConfigurationProperty;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.prefs.Preferences;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

public class AdminSettingsPanel extends AbstractSettingsPanel {

    private final CustomSettingsServletInterface settingsService;

    public AdminSettingsPanel() {
        super(Constants.SETTINGS_TAB_NAME);
        initComponents();
        this.settingsService = PlatformUI.MIRTH_FRAME.mirthClient.getServlet(CustomSettingsServletInterface.class);

        setVisibleTasks(0, 1, true);
    }

    public void doRefresh() {
        // if (PlatformUI.MIRTH_FRAME.alertRefresh()) {
        // return;
        // }
        // close any open cell editor before saving
        // if (this.customSettingsTable.getCellEditor() != null) {
        // this.customSettingsTable.getCellEditor().stopCellEditing();
        // }

        // final String workingId = getFrame().startWorking("Loading " + getTabName() +
        // " settings...");

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            List<CustomSetting> customSettingsList = null;

            public Void doInBackground() {
                try {
                    customSettingsList = settingsService.getAllSettings();
                } catch (ClientException e) {
                    // getFrame().alertThrowable(getFrame(), e);
                    System.out.println("Error: " + e.getMessage());
                }
                return null;
            }

            @Override
            public void done() {
                // null if it failed to get the settings
                if (customSettingsList != null) {
                    // update settings table function call
                    System.out.println("Custom Settings loaded: " + customSettingsList.size());
                }
                // getFrame().stopWorking(workingId);
            }
        };

        worker.execute();
    }

    public boolean doSave() {
        return true;
    }

    private void updateCustomSettingsTable(List<CustomSetting> settings, boolean sort) {

    }

    // private void updateConfigurationTable(Map<String, ConfigurationProperty> map,
    // boolean show, boolean sort) {
    // RefreshTableModel model = (RefreshTableModel) customSettingsTable.getModel();
    // String[][] data = new String[map.size()][3];
    // Map<String, ConfigurationProperty> sortedMap = null;
    // if (sort) {
    // sortedMap = new TreeMap<String,
    // ConfigurationProperty>(String.CASE_INSENSITIVE_ORDER);
    // sortedMap.putAll(map);
    // } else {
    // sortedMap = map;
    // }

    // int index = 0;
    // for (Entry<String, ConfigurationProperty> entry : sortedMap.entrySet()) {
    // data[index][0] = entry.getKey();
    // data[index][1] = entry.getValue().getValue();
    // data[index++][2] = entry.getValue().getComment();
    // }

    // model.refreshDataVector(data);
    // }

    private void initComponents() {
        setBackground(Color.WHITE);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setLayout(new MigLayout("insets 12, fill"));

        customSettingsTable = new MirthTable();
        // customSettingsTable.putClientProperty("terminateEditOnFocusLost",
        // Boolean.TRUE);
        // customSettingsTable.getTableHeader().setReorderingAllowed(false);
        // customSettingsTable.setSortable(false);
        // customSettingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // customSettingsTable.setModel(new RefreshTableModel(new String[][] {}, new
        // String[] {
        // "Id", "Key", "Value", "Description" }));
        // TableCellEditor cellEditor = new TextFieldCellEditor() {

        // @Override
        // protected boolean valueChanged(String value) {
        // PlatformUI.MIRTH_FRAME.setSaveEnabled(true);
        // return true;
        // }

        // };
        // customSettingsTable.getColumnExt("Key").setCellEditor(cellEditor);
        // customSettingsTable.getColumnExt("Comment").setCellEditor(cellEditor);
        // customSettingsTable.getSelectionModel().addListSelectionListener(new
        // ListSelectionListener() {
        // public void valueChanged(ListSelectionEvent evt) {
        // int selectedRow;
        // if (customSettingsTable.isEditing()) {
        // selectedRow = customSettingsTable.getEditingRow();
        // } else {
        // selectedRow = customSettingsTable.getSelectedRow();
        // }
        // removeButton.setEnabled(selectedRow != -1);
        // }
        // });

        // customSettingsTable.getColumnExt("Value")
        // .setCellEditor(new MirthDialogTableCellEditor(customSettingsTable));

        // if (Preferences.userNodeForPackage(Mirth.class).getBoolean("highlightRows",
        // true)) {
        // customSettingsTable.setHighlighters(HighlighterFactory
        // .createAlternateStriping(UIConstants.HIGHLIGHTER_COLOR,
        // UIConstants.BACKGROUND_COLOR));
        // }

        customSettingsScrollPane = new JScrollPane();
        // customSettingsScrollPane.setViewportView(customSettingsTable);

        addButton = new MirthButton("Add");
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // ((RefreshTableModel) customSettingsTable.getModel()).addRow(new String[] {
                // "",
                // "" });

                // if (customSettingsTable.getRowCount() == 1) {
                // customSettingsTable.setRowSelectionInterval(0, 0);
                // }

                // PlatformUI.MIRTH_FRAME.setSaveEnabled(true);
            }

        });
        removeButton = new MirthButton("Remove");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // if (customSettingsTable.getSelectedModelIndex() != -1 &&
                // !customSettingsTable.isEditing()) {
                // Integer selectedModelIndex = customSettingsTable.getSelectedModelIndex();

                // RefreshTableModel model = (RefreshTableModel) customSettingsTable.getModel();

                // int newViewIndex =
                // customSettingsTable.convertRowIndexToView(selectedModelIndex);
                // if (newViewIndex == (model.getRowCount() - 1)) {
                // newViewIndex--;
                // }

                // // must set lastModelRow to -1 so that when setting the new
                // // row selection below the old data won't try to be saved.
                // model.removeRow(selectedModelIndex);

                // if (model.getRowCount() != 0) {
                // customSettingsTable.setRowSelectionInterval(newViewIndex, newViewIndex);
                // }

                // PlatformUI.MIRTH_FRAME.setSaveEnabled(true);
                // }
            }

        });

        customSettingsPanel = new JPanel();
        customSettingsPanel.setBackground(Color.WHITE);
        customSettingsPanel.setLayout(new MigLayout("fill, insets 0", "[grow]", "[][grow]"));
        customSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(204, 204, 204)),
                "Custom Settings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        JPanel configurationMapSubPanel = new JPanel();
        configurationMapSubPanel.setBackground(Color.WHITE);
        configurationMapSubPanel.setLayout(new MigLayout("fill, flowy, insets 0", "[grow][]", "[grow]"));
        configurationMapSubPanel.add(customSettingsScrollPane, "grow, wrap");
        configurationMapSubPanel.add(addButton, "growx, aligny top, split");
        configurationMapSubPanel.add(removeButton, "growx, aligny top");
        customSettingsPanel.add(configurationMapSubPanel, "grow, aligny top");

        add(customSettingsPanel, "grow, height 100px:100%:100%, wrap");
    }

    private JPanel customSettingsPanel;
    private JScrollPane customSettingsScrollPane;
    private MirthTable customSettingsTable;
    private MirthButton addButton;
    private MirthButton removeButton;
}
