# SyncDB
同步两个不同系统之间的数据，将A系统用户和部门数据同步到B系统用户和部门表;并为用户在角色表插入的默认角色记录
需求：
    系统均为SQLServer，id字段的类型不一致，需要做数据关联
所用技术：
    spring,mybatis,dbcp
    
 执行方式
    java -jar dbToDb.jar
    
 执行规则
    1.第一次开启程序全量更新
    2.第二次为设置的2个小时候更新（只要检查到A系统数据altertime 为最新修改时间就更新）
      2.1 最新修改有效时间 > 执行时间+2小时（配置）
