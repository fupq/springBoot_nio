package com.itmayiedu.ds_informationSchema.entity;

public class SchemaPrivileges {
    private String grantee;

    private String table_Catalog;

    private String table_Schema;

    private String privilege_Type;

    private String is_Grantable;

    /**
     * 返回schemaPrivileges信息
     * @return
     */
    public String printInfo(){
    	return "grantee:" + this.grantee + ",privilegeType:" + this.privilege_Type + ",tableSchema:" + this.table_Schema + ",tableCatalog:" + this.table_Catalog + ",isGrantable:" + this.is_Grantable;
    }
    
    public String getGrantee() {
        return grantee;
    }

    public void setGrantee(String grantee) {
        this.grantee = grantee == null ? null : grantee.trim();
    }

    public String getTableCatalog() {
        return table_Catalog;
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

    public String getPrivilegeType() {
        return privilege_Type;
    }

    public void setPrivilegeType(String privilegeType) {
        this.privilege_Type = privilegeType == null ? null : privilegeType.trim();
    }

    public String getIsGrantable() {
        return is_Grantable;
    }

    public void setIsGrantable(String isGrantable) {
        this.is_Grantable = isGrantable == null ? null : isGrantable.trim();
    }
}