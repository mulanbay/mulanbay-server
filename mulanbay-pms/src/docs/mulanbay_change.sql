# 这是小版本间的sql更新记录

#3.4
# 消费记录增加预期作废时间
ALTER TABLE `buy_record` ADD COLUMN `expect_delete_date` DATETIME NULL AFTER `delete_date`;
# 消费记录增加父级ID，级联使用
ALTER TABLE `buy_record` ADD COLUMN `pid` BIGINT(20) NULL AFTER `sku_info`;

# 商品的子类ID列表(递归)
CREATE FUNCTION `getBuyRecordChildren`(rootId BigInt) RETURNS varchar(1000) CHARSET utf8mb4 COLLATE utf8mb4_general_ci
BEGIN
	DECLARE ptemp VARCHAR(1000);
	DECLARE ctemp VARCHAR(1000);
	SET ptemp = '#';
	SET ctemp = CAST(rootId AS CHAR);
	WHILE ctemp IS NOT NULL DO
		SET ptemp = CONCAT(ptemp,',',ctemp);
SELECT GROUP_CONCAT(id) INTO ctemp FROM buy_record
WHERE FIND_IN_SET(pid, ctemp) > 0;
END WHILE;
    set ptemp = replace(ptemp,concat('#,',rootId),'');
RETURN ptemp;
END