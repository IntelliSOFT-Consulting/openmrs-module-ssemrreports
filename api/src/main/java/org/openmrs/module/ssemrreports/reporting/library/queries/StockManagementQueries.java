package org.openmrs.module.ssemrreports.reporting.library.queries;

public class StockManagementQueries {
	
	public static String getProductStockLevels() {
		String query = "SELECT " + " IFNULL(product_group, '') AS product_group," + " IFNULL(sku, '') AS sku,"
		        + " IFNULL(product_name, '') AS product_name," + " IFNULL(opening, '') AS opening,"
		        + " IFNULL(closing,'') AS closing, " + " IFNULL(receive,'') AS receive, " + " IFNULL(issue,'') AS issue, "
		        + " IFNULL(dispense,'') AS dispense, " + " IFNULL(expiry,'') AS expiry, " + " IFNULL(loss,'') AS loss, "
		        + " IFNULL(wastage,'') AS wastage, " + " IFNULL(adjustment,'') AS adjustment, " + " IFNULL(amc,'') AS amc, "
		        + " IFNULL(mos,'') AS mos, " + " IFNULL(so_days,'') AS so_days, " + " IFNULL(order_qty,'') AS order_qty "
		        + " FROM etl_inv_stock_report " + " WHERE start_date BETWEEN :startDate AND :endDate ";
		return query;
	}
	
}
