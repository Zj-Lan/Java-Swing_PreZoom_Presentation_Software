package prezoom.view.table;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * The custom table renderer for the cells of JTables that has Color values
 * @author Oracle<p>
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 **/
public class ColorRenderer extends JLabel
        implements TableCellRenderer
{
    Border unselectedBorder = null;
    Border selectedBorder = null;
    boolean isBordered;

    /**
     * the constructor
     * @param isBordered true to have border
     *                   false to fill the cell
     */
    public ColorRenderer(boolean isBordered)
    {
        this.isBordered = isBordered;
        setOpaque(true); //MUST do this for background to show up.
    }

    /**
     * {@inheritDoc}
     */
    public Component getTableCellRendererComponent(
            JTable table, Object color,
            boolean isSelected, boolean hasFocus,
            int row, int column)
    {
        Color newColor = (Color) color;
        setBackground(newColor);
        if (isBordered)
        {
            if (isSelected)
            {
                if (selectedBorder == null)
                {
                    selectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5,
                            table.getSelectionBackground());
                }
                setBorder(selectedBorder);
            } else
            {
                if (unselectedBorder == null)
                {
                    unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2, 5,
                            table.getBackground());
                }
                setBorder(unselectedBorder);
            }
        }

        setToolTipText("RGB value: " + newColor.getRed() + ", "
                + newColor.getGreen() + ", "
                + newColor.getBlue());
        return this;
    }
}
