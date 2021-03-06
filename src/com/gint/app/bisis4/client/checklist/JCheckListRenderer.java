package com.gint.app.bisis4.client.checklist;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

public class JCheckListRenderer extends CheckRenderer implements ListCellRenderer {
  public JCheckListRenderer() {
    check.setBackground(UIManager.getColor("List.textBackground"));
    label.setForeground(UIManager.getColor("List.textForeground"));
    commonIcon = UIManager.getIcon("Tree.leafIcon");
  }

  public Component getListCellRendererComponent(JList list, Object value,
             int index, boolean isSelected, boolean hasFocus) {
    setEnabled(list.isEnabled());
    check.setSelected(((CheckableItem)value).isSelected());
    label.setFont(list.getFont());
    label.setText(value.toString());
    label.setSelected(isSelected);
    label.setFocus(hasFocus);
    Icon icon = ((CheckableItem)value).getIcon();
    if (icon == null) {
      icon = commonIcon;
    }
    //label.setIcon(icon);
    return this;
  }

  Icon commonIcon;
}
