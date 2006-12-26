/****************************************************************************
Copyright (c) 2006, Colorado School of Mines and others. All rights reserved.
This program and accompanying materials are made available under the terms of
the Common Public License - v1.0, which accompanies this distribution, and is
available at http://www.eclipse.org/legal/cpl-v10.html
****************************************************************************/
package edu.mines.jtk.mosaic;

import edu.mines.jtk.dsp.Sampling;

/**
 * A simple plot is easy to construct and especially useful for quick
 * diagnostic plots of arrays of floats or doubles. Specifically, a
 * simple plot is a plot frame with only one plot panel.
 * <p>
 * For example, a simple plot of an array float[] f can be displayed with
 * <pre><code>
 *   SimplePlot.asSequence(f);
 * </code></pre>
 * Likewise, a simple plot of an array float[][] f can be displayed with
 * <pre><code>
 *   SimplePlot.asPixels(f);
 * </code></pre>
 * The plots in these examples use default parameters and cannot be
 * customized easily. More complex plots can be constructed as in this
 * example:
 * <pre><code>
 *   SimplePlot plot = new SimplePlot();
 *   plot.addGrid("H-.V-.");
 *   PointsView pv = plot.addPoints(f);
 *   pv.setStyle("r-o");
 *   plot.setTitle("A plot of an array");
 *   plot.setVLabel("array value");
 *   plot.setHLabel("array index");
 * </code></pre>
 * @author Dave Hale, Colorado School of Mines
 * @version 2006.12.26
 */
public class SimplePlot extends PlotFrame {
  private static final long serialVersionUID = 1L;

  /**
   * The origin can be either at the upper-left or lower-left of the plot.
   * The default is lower-left for plots of 1-D arrays and upper-left for
   * plots of 2-D arrays.
   */
  public enum Origin {
    LOWER_LEFT,
    UPPER_LEFT
  }

  /**
   * Constructs a simple plot with default lower-left origin.
   */
  public SimplePlot() {
    this(Origin.LOWER_LEFT);
  }

  /**
   * Constructs a simple plot with specified origin.
   * @param origin the plot origin.
   */
  public SimplePlot(Origin origin) {
    super(makePanel(origin));
    this.setDefaultCloseOperation(PlotFrame.EXIT_ON_CLOSE);
    this.setFontSize(24);
    this.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    this.setVisible(true);
  }

  /**
   * Returns a new plot with a points view of specified values f(x).
   * Uses default sampling of x = 0, 1, 2, ....
   * @param f array of sampled function values f(x).
   * @return the plot.
   */
  public static SimplePlot asPoints(float[] f) {
    SimplePlot plot = new SimplePlot(Origin.LOWER_LEFT);
    plot.addPoints(f);
    return plot;
  }

  /**
   * Returns a new plot with a sequence view of specified values f(x).
   * Uses default sampling of x = 0, 1, 2, ....
   * @param f array of sampled function values f(x).
   * @return the plot.
   */
  public static SimplePlot asSequence(float[] f) {
    SimplePlot plot = new SimplePlot(Origin.LOWER_LEFT);
    plot.addSequence(f);
    return plot;
  }

  /**
   * Returns a new plot with a pixels view of a sampled function f(x1,x2).
   * Assumes zero first-sample values and unit sampling intervals.
   * @param f array[n2][n1] of sampled function values f(x1,x2), where
   *  n1 = f[0].length and n2 = f.length.
   * @return the plot.
   */
  public static SimplePlot asPixels(float[][] f) {
    SimplePlot plot = new SimplePlot(Origin.UPPER_LEFT);
    PixelsView pv = plot.addPixels(f);
    pv.setInterpolation(PixelsView.Interpolation.NEAREST);
    return plot;
  }

  /**
   * Returns a new plot with a sequence view of specified values f(x).
   * Uses default sampling of x = 0, 1, 2, ....
   * @param f array of sampled function values f(x).
   * @return the plot.
   */
  public static SimplePlot asSequence(double[] f) {
    return SimplePlot.asSequence(convertToFloat(f));
  }

  /**
   * Returns a new plot with a pixels view of a sampled function f(x1,x2).
   * Assumes zero first-sample values and unit sampling intervals.
   * @param f array[n2][n1] of sampled function values f(x1,x2), where
   *  n1 = f[0].length and n2 = f.length.
   * @return the plot.
   */
  public static SimplePlot asPixels(double[][] f) {
    return SimplePlot.asPixels(convertToFloat(f));
  }

  /**
   * Adds a grid view.
   * @return the grid view.
   */
  public GridView addGrid() {
    return _panel.addGrid();
  }

  /**
   * Adds a grid view with specified parameters string.
   * For the format of the parameters string, see
   * {@link edu.mines.jtk.mosaic.GridView#setParameters(String)}.
   * @param parameters the parameters string.
   * @return the grid view.
   */
  public GridView addGrid(String parameters) {
    return _panel.addGrid(parameters);
  }

  /**
   * Adds a pixels view of the specified sampled function f(x1,x2).
   * Assumes zero first sample values and unit sampling intervals.
   * @param f array[n2][n1] of sampled function values f(x1,x2), where
   *  n1 = f[0].length and n2 = f.length.
   * @return the pixels view.
   */
  public PixelsView addPixels(float[][] f) {
    return _panel.addPixels(f);
  }

  /**
   * Adds a pixels view of the specified sampled function f(x1,x2).
   * @param s1 the sampling of the variable x1; must be uniform.
   * @param s2 the sampling of the variable x2; must be uniform.
   * @param f array[n2][n1] of sampled function values f(x1,x2), where
   *  n1 = f[0].length and n2 = f.length.
   * @return the pixels view.
   */
  public PixelsView addPixels(Sampling s1, Sampling s2, float[][] f) {
    return _panel.addPixels(s1,s2,f);
  }


  /**
   * Adds a points view of the arrays x1 and x2 of point (x1,x2) coordinates.
   * @param x1 array of x1 coordinates.
   * @param x2 array of x2 coordinates.
   * @return the points view.
   */
  public PointsView addPoints(float[] x1, float[] x2) {
    return _panel.addPoints(x1,x2);
  }

  /**
   * Adds a points view of (x1,x2) with specified x2 coordinates.
   * The corresponding coordinates x1 are assumed to be 0, 1, 2, ....
   * @param x2 array of x2 coordinates.
   * @return the points view.
   */
  public PointsView addPoints(float[] x2) {
    return _panel.addPoints(x2);
  }

  /**
   * Adds a view of points (x1,x2) for a sampled function x2(x1).
   * @param s1 the sampling of x1 coordinates.
   * @param x2 array of x2 coordinates.
   * @return the points view.
   */
  public PointsView addPoints(Sampling s1, float[] x2) {
    return _panel.addPoints(s1,x2);
  }

  /**
   * Adds a sequence view with specified values f(x).
   * Uses default sampling of x = 0, 1, 2, ....
   * @param f array of sampled function values f(x).
   * @return the sequence view.
   */
  public SequenceView addSequence(float[] f) {
    return _panel.addSequence(f);
  }

  /**
   * Adds a sequence view with specified sampling and values f(x).
   * @param sx the sampling of the variable x.
   * @param f array of sampled function values f(x).
   * @return the sequence view.
   */
  public SequenceView addSequence(Sampling sx, float[] f) {
    return _panel.addSequence(sx,f);
  }

  /**
   * Adds the color bar with no label. The color bar paints the color map
   * of the most recently added pixels view. To avoid confusion, a color
   * bar should perhaps not be added when this plot contains more  than
   * one pixels view.
   * @return the color bar.
   */
  public ColorBar addColorBar() {
    return _panel.addColorBar();
  }

  /**
   * Adds the color bar with specified label.
   * @param label the label; null, if none.
   * @return the color bar.
   */
  public ColorBar addColorBar(String label) {
    return _panel.addColorBar(label);
  }

  /**
   * Adds the specified tiled view to this plot's panel. If the tiled view
   * is already in this panel, it is first removed, before adding it again.
   * @param tv the tiled view.
   * @return true, if this panel did not already contain the specified
   *  tiled view; false, otherwise.
   */
  public boolean add(TiledView tv) {
    return _panel.addTiledView(tv);
  }

  /**
   * Removes the specified tiled view from this plot's panel.
   * @param tv the tiled view.
   * @return true, if this panel contained the specified tiled view;
   *  false, otherwise.
   */
  public boolean remove(TiledView tv) {
    return _panel.remove(tv);
  }

  /**
   * Adds the plot title. Equivalent to {@link #setTitle(String)}.
   * The title font is 1.5 times larger than the font of this plot.
   * @param title the title; null, if none.
   */
  public void addTitle(String title) {
    _panel.addTitle(title);
  }

  /**
   * Sets the plot title. Equivalent to {@link #addTitle(String)}.
   * @param title the title; null, for no title.
   */
  public void setTitle(String title) {
    _panel.setTitle(title);
  }

  /**
   * Removes the plot title. Equivalent to calling the method
   * {@link #setTitle(String)} with a null title.
   */
  public void removeTitle() {
    _panel.removeTitle();
  }

  /**
   * Sets limits for the both horizontal and vertical axes.
   * By default, limits are computed automatically by graphical views.
   * This method can be used to override those default limits.
   * @param hmin the minimum value.
   * @param vmin the minimum value.
   * @param hmax the maximum value.
   * @param vmax the maximum value.
   */
  public void setLimits(double hmin, double vmin, double hmax, double vmax) {
    _panel.setLimits(hmin,vmin,hmax,vmax);
  }

  /**
   * Sets limits for the horizontal axis.
   * By default, limits are computed automatically by graphical views.
   * This method can be used to override those default limits.
   * @param hmin the minimum value.
   * @param hmax the maximum value.
   */
  public void setHLimits(double hmin, double hmax) {
    _panel.setHLimits(hmin,hmax);
  }

  /**
   * Sets limits for the vertical axis.
   * By default, limits are computed automatically by graphical views.
   * This method can be used to override those default limits.
   * @param vmin the minimum value.
   * @param vmax the maximum value.
   */
  public void setVLimits(double vmin, double vmax) {
    _panel.setVLimits(vmin,vmax);
  }

  /**
   * Sets default limits for horizontal and vertical axes. This method may
   * be used to restore default limits after they have been set explicitly.
   */
  public void setLimitsDefault() {
    _panel.setLimitsDefault();
  }

  /**
   * Sets default limits for the horizontal axis. This method may be used
   * to restore default limits after they have been set explicitly.
   */
  public void setHLimitsDefault() {
    _panel.setHLimitsDefault();
  }

  /**
   * Sets default limits for the vertical axis. This method may be used
   * to restore default limits after they have been set explicitly.
   */
  public void setVLimitsDefault() {
    _panel.setVLimitsDefault();
  }

  /**
   * Sets the label for the horizontal axis.
   * @param label the label.
   */
  public void setHLabel(String label) {
    _panel.setHLabel(label);
  }

  /**
   * Sets the label for the vertical axis.
   * @param label the label.
   */
  public void setVLabel(String label) {
    _panel.setVLabel(label);
  }

  /**
   * Sets the format for the horizontal axis.
   * @param format the format.
   */
  public void setHFormat(String format) {
    _panel.setHFormat(format);
  }

  /**
   * Sets the format for the vertical axis.
   * @param format the format.
   */
  public void setVFormat(String format) {
    _panel.setVFormat(format);
  }

  /**
   * Gets the plot panel for this plot.
   * @return the plot panel.
   */
  public PlotPanel getPlotPanel() {
    return _panel;
  }

  ///////////////////////////////////////////////////////////////////////////
  // private

  private static final int DEFAULT_WIDTH = 800;
  private static final int DEFAULT_HEIGHT = 700;

  private PlotPanel _panel = super.getPlotPanel(); // one panel in plot

  private static PlotPanel makePanel(Origin origin) {
    if (origin==Origin.LOWER_LEFT)
      return new PlotPanel(PlotPanel.Orientation.X1RIGHT_X2UP);
    else
      return new PlotPanel(PlotPanel.Orientation.X1DOWN_X2RIGHT);
  }

  private static float[] convertToFloat(double[] a) {
    int n = a.length;
    float[] b = new float[n];
    for (int i=0; i<n; ++i) {
      b[i] = (float)a[i];
    }
    return b;
  }

  private static float[][] convertToFloat(double[][] a) {
    int n2 = a.length;
    float[][] b = new float[n2][];
    for (int i2=0; i2<n2; ++i2) {
      int n1 = a[i2].length;
      for (int i1=0; i1<n1; ++i1) {
        b[i2][i1] = (float)a[i2][i1];
      }
    }
    return b;
  }
}