package com.fh.attr.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产品满减表(只针对同商品)
 * </p>
 *
 * @author yhren
 * @since 2020-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pms_product_full_reduction")
public class PmsProductFullReduction implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productId;

    private BigDecimal fullPrice;

    private BigDecimal reducePrice;


}
