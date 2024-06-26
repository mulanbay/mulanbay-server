# 这是小版本间的sql更新记录

#v3.4开始
# 消费记录增加预期作废时间
ALTER TABLE `buy_record` ADD COLUMN `expect_delete_date` DATETIME NULL AFTER `delete_date`;
# 消费记录增加父级ID，级联使用
ALTER TABLE `buy_record` ADD COLUMN `pid` BIGINT(20) NULL AFTER `sku_info`;

# 商品的子类ID列表(递归)
DELIMITER ;;
CREATE  FUNCTION `getBuyRecordChildren`(rootId BigInt) RETURNS varchar(1000)
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
END ;;
DELIMITER ;

# 看病记录增加门诊阶段
ALTER TABLE `treat_record` ADD COLUMN `stage` SMALLINT(5) NULL DEFAULT 0 AFTER `tags`;

# 消费记录增加看病记录外键
ALTER TABLE `buy_record` ADD COLUMN `treat_record_id` BIGINT(20) NULL AFTER `pid`;
# 用户设置增加偏好
ALTER TABLE `user_setting`
ADD COLUMN `treat_goods_type_id` INT NULL AFTER `resident_city`,
ADD COLUMN `treat_sub_goods_type_id` INT NULL AFTER `treat_goods_type_id`,
ADD COLUMN `treat_buy_type_id` INT NULL AFTER `treat_sub_goods_type_id`,
ADD COLUMN `payment` SMALLINT(5) NULL DEFAULT 0 AFTER `treat_sub_goods_type_id`,
ADD COLUMN `buy_type_id` INT NULL AFTER `payment`;

#同步看病记录到消费记录（可选）
INSERT INTO buy_record
(`user_id`,
 `buy_type_id`,
 `goods_type_id`,
 `sub_goods_type_id`,
 `goods_name`,
 `shop_name`,
 `price`,
 `amount`,
 `shipment`,
 `total_price`,
 `payment`,
 `buy_date`,
 `consume_date`,
 `status`,
 `secondhand`,
 `keywords`,
 `statable`,
 `consume_type`,
 `treat_record_id`,
 `remark`,
 `created_time`)
SELECT user_id,27,104,105,CONCAT('看病：',hospital,',',disease) as goodsName,hospital,personal_paid_fee,1,0,personal_paid_fee,1,treat_date,treat_date,0,0,tags,1,2,id,'自动导入',now()
FROM treat_record;

#删除资金类型字段
ALTER TABLE budget DROP COLUMN `fee_type`;
ALTER TABLE budget_snapshot DROP COLUMN `fee_type`;

#商品类型增加标签
ALTER TABLE `goods_type` ADD COLUMN `tags` VARCHAR(256) NULL AFTER `statable`;

#v3.4结束

#v4.0开始
#机器学习模型配置
CREATE TABLE `model_config` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  `code` VARCHAR(64) NOT NULL,
  `file_name` VARCHAR(128) NOT NULL,
  `du` TINYINT NOT NULL,
  `algorithm` SMALLINT(5) NOT NULL,
  `status` SMALLINT(5) NOT NULL,
  `remark` VARCHAR(200) NULL,
  `created_time` DATETIME NOT NULL,
  `last_modify_time` DATETIME NULL,
  PRIMARY KEY (`id`));

#阅读记录增加图书来源
ALTER TABLE `reading_record` ADD COLUMN `source` SMALLINT(5) NULL AFTER `status`;
ALTER TABLE `reading_record` ADD COLUMN `secondhand` TINYINT NULL DEFAULT 0 AFTER `source`;

#国家
CREATE TABLE `country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cn_name` VARCHAR(64) NULL,
  `en_name` VARCHAR(64) NULL,
  `en_code2` VARCHAR(32) NULL,
  `en_code3` VARCHAR(32) NULL,
  `code` VARCHAR(32) NULL,
  `location` VARCHAR(64) NULL,
  `order_index` SMALLINT(5) NULL,
  `status` SMALLINT(5) NULL DEFAULT 1,
  `remark` VARCHAR(200) NULL,
  PRIMARY KEY (`id`));

#阅读记录的国家修改为国家表
ALTER TABLE `reading_record` ADD COLUMN `country_id` INT NULL AFTER `press`;
ALTER TABLE `reading_record` DROP COLUMN `nation`;
ALTER TABLE `life_experience_detail` ADD COLUMN `country_id` INT NULL AFTER `life_experience_id`;
ALTER TABLE `life_experience_detail` DROP COLUMN `country`;

#消费记录增加使用时长
ALTER TABLE `buy_record` ADD COLUMN `use_time` BIGINT(20) NULL AFTER `expect_delete_date`;
update buy_record set use_time = ( unix_timestamp(delete_date) -unix_timestamp(buy_date))*1000 where delete_date is not null and id>0;

