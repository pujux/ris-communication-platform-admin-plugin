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
import com.dedalus.adminplugin.shared.interfaces.CustomMappingsServletInterface;
import com.dedalus.adminplugin.shared.model.CustomMapping;
import com.mirth.connect.client.core.ClientException;
import com.mirth.connect.client.ui.AbstractSettingsPanel;
import com.mirth.connect.client.ui.PlatformUI;
import com.mirth.connect.client.ui.components.MirthButton;
import com.mirth.connect.client.ui.components.MirthTable;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminMappingsPanel extends AbstractSettingsPanel {

    private final CustomMappingsServletInterface mappingsService;

    public AdminMappingsPanel() {
        super(Constants.MAPPINGS_TAB_NAME);
        initComponents();
        this.mappingsService = PlatformUI.MIRTH_FRAME.mirthClient.getServlet(CustomMappingsServletInterface.class);

        setVisibleTasks(0, 1, true);
    }

    public void doRefresh() {
        if (PlatformUI.MIRTH_FRAME.alertRefresh()) {
            return;
        }
        // close any open cell editor before saving
        if (this.customMappingsTable.getCellEditor() != null) {
            this.customMappingsTable.getCellEditor().stopCellEditing();
        }

        final String workingId = getFrame().startWorking("Loading " + getTabName() + " ...");

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

            List<CustomMapping> customMappingValuesList = null;

            public Void doInBackground() {
                try {
                    customMappingValuesList = mappingsService.getAllMappings();
                } catch (ClientException e) {
                    getFrame().alertThrowable(getFrame(), e);
                }
                return null;
            }

            @Override
            public void done() {
                // null if it failed to get the mapping values
                if (customMappingValuesList != null) {
                    // update mapping value table function call
                    System.out.println("Custom Mapping Values loaded: " + customMappingValuesList.size());
                }
                getFrame().stopWorking(workingId);
            }
        };

        worker.execute();
    }

    public boolean doSave() {
        return true;
    }

    // private void updateCustomMappingsTable(List<CustomMapping> mappings, boolean
    // sort) {

    // }

    // private void updateConfigurationTable(Map<String, ConfigurationProperty> map,
    // boolean show, boolean sort) {
    // RefreshTableModel model = (RefreshTableModel) customMappingsTable.getModel();
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

        customMappingsTable = new MirthTable();
        // customMappingsTable.putClientProperty("terminateEditOnFocusLost",
        // Boolean.TRUE);
        // customMappingsTable.getTableHeader().setReorderingAllowed(false);
        // customMappingsTable.setSortable(false);
        // customMappingsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // customMappingsTable.setModel(new RefreshTableModel(new String[][] {}, new
        // String[] {
        // "Id", "Key", "Value", "Description" }));
        // TableCellEditor cellEditor = new TextFieldCellEditor() {

        // @Override
        // protected boolean valueChanged(String value) {
        // PlatformUI.MIRTH_FRAME.setSaveEnabled(true);
        // return true;
        // }

        // };
        // customMappingsTable.getColumnExt("Key").setCellEditor(cellEditor);
        // customMappingsTable.getColumnExt("Comment").setCellEditor(cellEditor);
        // customMappingsTable.getSelectionModel().addListSelectionListener(new
        // ListSelectionListener() {
        // public void valueChanged(ListSelectionEvent evt) {
        // int selectedRow;
        // if (customMappingsTable.isEditing()) {
        // selectedRow = customMappingsTable.getEditingRow();
        // } else {
        // selectedRow = customMappingsTable.getSelectedRow();
        // }
        // removeButton.setEnabled(selectedRow != -1);
        // }
        // });

        // customMappingsTable.getColumnExt("Value")
        // .setCellEditor(new MirthDialogTableCellEditor(customMappingsTable));

        // if (Preferences.userNodeForPackage(Mirth.class).getBoolean("highlightRows",
        // true)) {
        // customMappingsTable.setHighlighters(HighlighterFactory
        // .createAlternateStriping(UIConstants.HIGHLIGHTER_COLOR,
        // UIConstants.BACKGROUND_COLOR));
        // }

        customMappingsScrollPane = new JScrollPane();
        // customMappingsScrollPane.setViewportView(customMappingsTable);

        addButton = new MirthButton("Add");
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // ((RefreshTableModel) customMappingsTable.getModel()).addRow(new String[] {
                // "",
                // "" });

                // if (customMappingsTable.getRowCount() == 1) {
                // customMappingsTable.setRowSelectionInterval(0, 0);
                // }

                // PlatformUI.MIRTH_FRAME.setSaveEnabled(true);
            }

        });
        removeButton = new MirthButton("Remove");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // if (customMappingsTable.getSelectedModelIndex() != -1 &&
                // !customMappingsTable.isEditing()) {
                // Integer selectedModelIndex = customMappingsTable.getSelectedModelIndex();

                // RefreshTableModel model = (RefreshTableModel) customMappingsTable.getModel();

                // int newViewIndex =
                // customMappingsTable.convertRowIndexToView(selectedModelIndex);
                // if (newViewIndex == (model.getRowCount() - 1)) {
                // newViewIndex--;
                // }

                // // must set lastModelRow to -1 so that when setting the new
                // // row selection below the old data won't try to be saved.
                // model.removeRow(selectedModelIndex);

                // if (model.getRowCount() != 0) {
                // customMappingsTable.setRowSelectionInterval(newViewIndex, newViewIndex);
                // }

                // PlatformUI.MIRTH_FRAME.setSaveEnabled(true);
                // }
            }

        });

        customMappingsPanel = new JPanel();
        customMappingsPanel.setBackground(Color.WHITE);
        customMappingsPanel.setLayout(new MigLayout("fill, insets 0", "[grow]", "[][grow]"));
        customMappingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(204, 204, 204)),
                "Custom Mappings", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        JPanel configurationMapSubPanel = new JPanel();
        configurationMapSubPanel.setBackground(Color.WHITE);
        configurationMapSubPanel.setLayout(new MigLayout("fill, flowy, insets 0", "[grow][]", "[grow]"));
        configurationMapSubPanel.add(customMappingsScrollPane, "grow, wrap");
        configurationMapSubPanel.add(addButton, "growx, aligny top, split");
        configurationMapSubPanel.add(removeButton, "growx, aligny top");
        customMappingsPanel.add(configurationMapSubPanel, "grow, aligny top");

        add(customMappingsPanel, "grow, height 100px:100%:100%, wrap");
    }

    private JPanel customMappingsPanel;
    private JScrollPane customMappingsScrollPane;
    private MirthTable customMappingsTable;
    private MirthButton addButton;
    private MirthButton removeButton;
}
