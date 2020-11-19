# 这是小版本间的sql更新记录
# 2020-11-19 人生经历增加地理位置信息
ALTER TABLE `life_experience`
ADD COLUMN `lc_name` VARCHAR(45) NULL AFTER `tags`,
ADD COLUMN `location` VARCHAR(45) NULL AFTER `lc_name`;
