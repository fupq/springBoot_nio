package com.itmayiedu.ds_informationSchema.entity;

import java.util.Date;

public class Tables {
    private String table_Catalog;

    private String table_Schema;

    private String table_Name;

    private String table_Type;

    private String engine;

    private Long version;

    private String row_Format;

    private Long table_Rows;

    private Long avg_Row_Length;

    private Long data_Length;

    private Long max_Data_Length;

    private Long index_Length;

    private Long data_Free;

    private Long auto_Increment;

    private Date create_Time;

    private Date update_Time;

    private Date check_Time;

    private String table_Collation;

    private Long checksum;

    private String create_Options;

    private String table_Comment;

    public String getTableCatalog() {
        return table_Catalog;
    }

    public String printInfo(){
    	String result = "table_Name:" + this.table_Name + ",table_Schema:" + this.table_Schema + ",table_Comment:" + this.table_Comment + ",table_Type:" + this.table_Type + ",create_Options:" + this.create_Options + ",engine:" + this.engine + ",row_Format:" + this.row_Format + ",table_Catalog:" + this.table_Catalog + ",table_Collation:" + this.table_Collation;
    	return result;
    }
    
    public void setTableCatalog(String tableCatalog) {
        this.table_Catalog = tableCatalog == null ? null : tableCatalog.trim();
    }

    public String getTableSchema() {
        return table_Schema;
    }

    public void setTableSchema(String tableSchema) {
        this.table_Schema = tableSchema == null ? null : tableSchema.trim();
    }

    public String getTableName() {
        return table_Name;
    }

    public void setTableName(String tableName) {
        this.table_Name = tableName == null ? null : tableName.trim();
    }

    public String getTableType() {
        return table_Type;
    }

    public void setTableType(String tableType) {
        this.table_Type = tableType == null ? null : tableType.trim();
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine == null ? null : engine.trim();
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getRowFormat() {
        return row_Format;
    }

    public void setRowFormat(String rowFormat) {
        this.row_Format = rowFormat == null ? null : rowFormat.trim();
    }

    public Long getTableRows() {
        return table_Rows;
    }

    public void setTableRows(Long tableRows) {
        this.table_Rows = tableRows;
    }

    public Long getAvgRowLength() {
        return avg_Row_Length;
    }

    public void setAvgRowLength(Long avgRowLength) {
        this.avg_Row_Length = avgRowLength;
    }

    public Long getDataLength() {
        return data_Length;
    }

    public void setDataLength(Long dataLength) {
        this.data_Length = dataLength;
    }

    public Long getMaxDataLength() {
        return max_Data_Length;
    }

    public void setMaxDataLength(Long maxDataLength) {
        this.max_Data_Length = maxDataLength;
    }

    public Long getIndexLength() {
        return index_Length;
    }

    public void setIndexLength(Long indexLength) {
        this.index_Length = indexLength;
    }

    public Long getDataFree() {
        return data_Free;
    }

    public void setDataFree(Long dataFree) {
        this.data_Free = dataFree;
    }

    public Long getAutoIncrement() {
        return auto_Increment;
    }

    public void setAutoIncrement(Long autoIncrement) {
        this.auto_Increment = autoIncrement;
    }

    public Date getCreateTime() {
        return create_Time;
    }

    public void setCreateTime(Date createTime) {
        this.create_Time = createTime;
    }

    public Date getUpdateTime() {
        return update_Time;
    }

    public void setUpdateTime(Date updateTime) {
        this.update_Time = updateTime;
    }

    public Date getCheckTime() {
        return check_Time;
    }

    public void setCheckTime(Date checkTime) {
        this.check_Time = checkTime;
    }

    public String getTableCollation() {
        return table_Collation;
    }

    public void setTableCollation(String tableCollation) {
        this.table_Collation = tableCollation == null ? null : tableCollation.trim();
    }

    public Long getChecksum() {
        return checksum;
    }

    public void setChecksum(Long checksum) {
        this.checksum = checksum;
    }

    public String getCreateOptions() {
        return create_Options;
    }

    public void setCreateOptions(String createOptions) {
        this.create_Options = createOptions == null ? null : createOptions.trim();
    }

    public String getTableComment() {
        return table_Comment;
    }

    public void setTableComment(String tableComment) {
        this.table_Comment = tableComment == null ? null : tableComment.trim();
    }
}