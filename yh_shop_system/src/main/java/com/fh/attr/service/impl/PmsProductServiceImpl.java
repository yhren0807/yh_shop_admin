package com.fh.attr.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.attr.entity.*;
import com.fh.attr.entity.Bo.ProductBo;
import com.fh.attr.mapper.PmsProductMapper;
import com.fh.attr.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author yhren
 * @since 2020-12-10
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements IPmsProductService {

    @Autowired
    private IPmsMemberPriceService iPmsMemberPriceService;
    @Autowired
    private IPmsProductLadderService iPmsProductLadderService;
    @Autowired
    private IPmsProductFullReductionService iPmsProductFullReductionService;
    @Autowired
    private IPmsProductAttributeValueService iPmsProductAttributeValueService;
    @Autowired
    private IPmsSkuStockService iPmsSkuStockService;


    @Override
    @Transactional
    public void saveData(ProductBo productBo) {
        PmsProduct pmsProduct=productBo;
        //新增主表数据
        boolean save = super.saveOrUpdate(pmsProduct);
        if (save){

            Long id = pmsProduct.getId();

            //新增会员价格数据
            saveMemberPrice(productBo.getMemberPriceList(),id);
            //新增阶梯价格数据
            saveProductLadder(productBo.getProductLadderList(),id);
            //新增满减价格数据
            saveFullReduction(productBo.getProductFullReductionList(),id);
            //新增属性数据
            saveProductAttr(productBo.getPmsProductAttributeList(),id);
            //新增库存表数据
            saveSkuStock(productBo.getSkuStockList(),id);
        }

    }

    @Override
    public ProductBo queryProductAll(Long productId) {
        //查询主表的数据
       PmsProduct productList = super.getOne(new QueryWrapper<PmsProduct>().eq("id", productId));
        ProductBo productBo=new ProductBo();
        BeanUtils.copyProperties(productList,productBo);
        //设置一个通用的查询条件
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("product_id",productId);
        productBo.setMemberPriceList(iPmsMemberPriceService.listByMap(map));
        productBo.setProductLadderList(iPmsProductLadderService.listByMap(map));
        productBo.setProductFullReductionList(iPmsProductFullReductionService.listByMap(map));
        productBo.setSkuStockList(iPmsSkuStockService.listByMap(map));
        productBo.setPmsProductAttributeList(iPmsProductAttributeValueService.listByMap(map));
        return productBo;
    }

    //新增会员价格数据
    private void saveMemberPrice(List<PmsMemberPrice> memberPriceList, Long productId) {
        memberPriceList.forEach(pmsMemberPrice -> {
            pmsMemberPrice.setProductId(productId);
        });
        iPmsMemberPriceService.remove(new QueryWrapper<PmsMemberPrice>().eq("product_id",productId));
        iPmsMemberPriceService.saveBatch(memberPriceList);
    }

    //新增阶梯价格数据
    private void saveProductLadder(List<PmsProductLadder> productLadderList, Long id) {
        productLadderList.forEach(pmsProductLadder -> {
            pmsProductLadder.setProductId(id);
        });
        iPmsProductLadderService.remove(new QueryWrapper<PmsProductLadder>().eq("product_id",id));
        iPmsProductLadderService.saveBatch(productLadderList);
    }

    //新增满减价格数据
    private void saveFullReduction(List<PmsProductFullReduction> productFullReductionList, Long id) {
        productFullReductionList.forEach(pmsProductFullReduction -> {
            pmsProductFullReduction.setProductId(id);
        });
        iPmsProductFullReductionService.remove(new QueryWrapper<PmsProductFullReduction>().eq("product_id",id));
        iPmsProductFullReductionService.saveBatch(productFullReductionList);
    }

    //新增属性数据
    private void saveProductAttr(List<PmsProductAttributeValue> pmsProductAttributeList, Long id) {
        pmsProductAttributeList.forEach(pmsProductAttributeValue -> {
            pmsProductAttributeValue.setProductId(id);
        });
        iPmsProductAttributeValueService.remove(new QueryWrapper<PmsProductAttributeValue>().eq("product_id",id));
        iPmsProductAttributeValueService.saveBatch(pmsProductAttributeList);

    }

    //新增库存表数据
    private void saveSkuStock(List<PmsSkuStock> skuStockList, Long id) {
        for (int i = 0; i <skuStockList.size(); i++) {
            String date= DateUtils.formatDate(new Date(),"yyyyMMdd");
            String productId=String.format("%06d",id);
            String strIndex=String.format("%03d",i);
            String join = StringUtils.join(date, productId, strIndex);
            skuStockList.get(i).setSkuCode(join);
            skuStockList.get(i).setProductId(id);
        }
        iPmsSkuStockService.remove(new QueryWrapper<PmsSkuStock>().eq("product_id",id));
        iPmsSkuStockService.saveBatch(skuStockList);

    }

}
