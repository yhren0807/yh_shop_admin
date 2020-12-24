package com.fh.attr.entity.Bo;/*--------
|姓名:
-------------
|作用:        |
--------------*/

import com.fh.attr.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class ProductBo extends PmsProduct {

    private List<PmsProductLadder> productLadderList;

    private List<PmsMemberPrice> memberPriceList;

    private List<PmsProductFullReduction> productFullReductionList;

    private List<PmsProductAttributeValue>  pmsProductAttributeList;

    private List<PmsSkuStock> skuStockList;

}
