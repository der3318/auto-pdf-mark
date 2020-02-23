UPDATE [trickster].[dbo].[tbl_item_option]
SET [option_value] = 9999 /* desired value of properties */
WHERE [item_uid] LIKE 4392015301 /* target item uid */
AND [option_index] >= 10000 AND [option_index] <= 100010;