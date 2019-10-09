package foodstart.ui.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import foodstart.manager.Managers;
import foodstart.model.order.Order;
import foodstart.ui.Refreshable;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;

/**
 * Controller class for analysis/graph view
 * 
 * @author Alex Hobson
 * @date 09/10/2019
 */
public class AnalysisController implements Refreshable {

	/**
	 * The type of chart to be showing currently
	 */
	ChartType chartType = ChartType.SALES_DOLLAR_MONTH;

	/**
	 * Format to display date in chart
	 */
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

	/**
	 * Chart object
	 */
	@FXML
	LineChart<Number, Number> mainchart;

	/**
	 * Prepare the chart for data
	 */
	@FXML
	public void initialize() {

		mainchart.getXAxis().setLabel("Date");

		((ValueAxis<Number>) mainchart.getXAxis()).setTickLabelFormatter(new StringConverter<Number>() {

			@Override
			public Number fromString(String arg0) {
				return LocalDate.parse(arg0, formatter).toEpochDay();
			}

			@Override
			public String toString(Number arg0) {
				return LocalDate.ofEpochDay(arg0.longValue()).format(formatter);
			}
		});

		((ValueAxis<Number>) mainchart.getYAxis()).setTickLabelFormatter(new StringConverter<Number>() {

			@Override
			public Number fromString(String arg0) {
				return Float.parseFloat(arg0);
			}

			@Override
			public String toString(Number arg0) {
				return String.valueOf(Math.round(arg0.floatValue() * 100F) / 100F);
			}
		});
		
		((ValueAxis<Number>) mainchart.getYAxis()).setAutoRanging(true);
		((ValueAxis<Number>) mainchart.getXAxis()).setAutoRanging(false);
		((NumberAxis) mainchart.getYAxis()).setForceZeroInRange(true);
		
		mainchart.getXAxis().setAnimated(false);
		mainchart.getYAxis().setAnimated(false);
		
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		mainchart.getData().clear();
		mainchart.getData().add(series);
	}

	public void displayChart(ChartType chartType) {
		XYChart.Series<Number, Number> series = mainchart.getData().get(0);
		series.getData().clear();
		
		int backDays = 365;
		switch (chartType) {
		case SALES_DOLLAR_MONTH:
			backDays = 30;
		case SALES_DOLLAR_YEAR:
			long backDay = LocalDate.now().toEpochDay() - backDays;
			Map<Long, Float> salesSummary = new HashMap<Long, Float>();
			for (Order order : Managers.getOrderManager().getOrderSet()) {
				long day = order.getTimePlaced().toLocalDate().toEpochDay();
				if (day >= backDay) {
					if (salesSummary.containsKey(day)) {
						salesSummary.put(day, salesSummary.get(day) + order.getTotalCost());
					} else {
						salesSummary.put(day, order.getTotalCost());
					}
				}
			}
			for (Map.Entry<Long, Float> entry : salesSummary.entrySet()) {
				series.getData().add(new XYChart.Data<Number, Number>(entry.getKey(), entry.getValue()));
			}
			
			((ValueAxis<Number>) mainchart.getXAxis()).setLowerBound(backDay);
			((ValueAxis<Number>) mainchart.getXAxis()).setUpperBound(backDay + backDays);
			mainchart.getYAxis().setLabel("Sales dollars");
			break;
		default:
			break;
		}
		

		mainchart.setTitle(chartType.getTitle());
	}

	@Override
	public void refreshTable() {
		displayChart(chartType);
	}

	public void salesDollarYr() {
		displayChart(ChartType.SALES_DOLLAR_YEAR);
	}

	public void salesDollarMonth() {
		displayChart(ChartType.SALES_DOLLAR_MONTH);
	}

	/**
	 * Enum of chart types
	 * 
	 * @author Alex Hobson
	 *
	 */
	private enum ChartType {
		SALES_DOLLAR_MONTH("Sales dollars per day (last 30 days)"), SALES_DOLLAR_YEAR(
				"Sales dollars per day (last 365 days)"), SALES_QTY_MONTH(
						"Sales per day (last 30 days)"), SALES_QTY_YEAR("Sales per day (last 365 days)");

		private final String title;

		private ChartType(String title) {
			this.title = title;
		}

		public String getTitle() {
			return this.title;
		}
	}
}
