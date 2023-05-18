# 这是小版本间的sql更新记录

#3.4
# 消费记录增加预期作废时间
ALTER TABLE `buy_record` ADD COLUMN `expect_delete_date` DATETIME NULL AFTER `delete_date`;
# 消费记录增加父级ID，级联使用
ALTER TABLE `buy_record` ADD COLUMN `pid` BIGINT(20) NULL AFTER `sku_info`;