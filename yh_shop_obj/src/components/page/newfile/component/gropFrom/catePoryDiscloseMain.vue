<template>
    <el-card class="form-container" shadow="never">
    <el-steps :active="active" finish-status="success">
        <el-step title="商品基本信息"></el-step>
        <el-step title="商品促销信息"></el-step>
        <el-step title="商品属性信息"></el-step>
        <el-step title="商品关联信息"></el-step>
    </el-steps>
    <cate-massage-one-from
     v-show="showFrom[0]"
     v-model="productForm"
     :is-edit="isEdit"
     @nextStep="nextStep"
    >
    </cate-massage-one-from>
    <cate-sales-two-from
    v-show="showFrom[1]"
    :is-edit="isEdit"
    v-model="productForm"
    @nextStep="nextStep"
    @prevStep="prevStep"
    >
    </cate-sales-two-from>
<cate-attr-three-from
v-show="showFrom[2]"
:is-edit="isEdit"
v-model="productForm"
@nextStep="nextStep"
@prevStep="prevStep"
>
</cate-attr-three-from>
   <cate-related-four-from
   v-show="showFrom[3]"
   :is-edit="isEdit"
   v-model="productForm"
   @prevStep="prevStep"
   >
   </cate-related-four-from>

    </el-card>
</template>
<script>
    import CateAttrThreeFrom from "./cateAttrThreeFrom";
    import CateMassageOneFrom from "./cateMassageOneFrom";
    import CateSalesTwoFrom from "./cateSalesTwoFrom"
    import CateRelatedFourFrom from "./cateRelatedFourFrom"

    import {queryProductAll} from "../../../../../api/catepory/catePoryGroup";

    const defaultProductParam={
        albumPics: '',
        brandId: null,
        brandName: '',
        deleteStatus: 0,
        description: '',
        detailDesc: '',
        detailHtml: '',
        detailMobileHtml: '',
        detailTitle: '',
        feightTemplateId: 0,
        flashPromotionCount: 0,
        flashPromotionId: 0,
        flashPromotionPrice: 0,
        flashPromotionSort: 0,
        giftPoint: 0,
        giftGrowth: 0,
        keywords: '',
        lowStock: 0,
        name: '',
        newStatus: 0,
        note: '',
        originalPrice: 0,
        pic: '',
        previewStatus: 0,
        price: 0,
        productAttributeCategoryId: null,
        cateParentId:null,
        productCategoryId: null,
        productCategoryName: '',
        productSn: '',
        promotionEndTime: '',
        promotionPerLimit: 0,
        promotionPrice: null,
        promotionStartTime: '',
        promotionType: 0,
        publishStatus: 0,
        recommandStatus: 0,
        sale: 0,
        serviceIds: '',
        sort: 0,
        stock: 0,
        subTitle: '',
        unit: '',
        usePointLimit: 0,
        verifyStatus: 0,
        weight: 0,

        //会员集合
        memberPriceList:[],
        productLadderList:[{count: 0,discount: 0,price: 0}],
        productFullReductionList:[{fullPrice:0,reducePrice:0}],
        skuStockList:[],
        pmsProductAttributeList:[],


    }

    export default {
        name: "catePoryDiscloseMain",
        components: {CateMassageOneFrom,queryProductAll,CateAttrThreeFrom,CateSalesTwoFrom,CateRelatedFourFrom},
        props: {
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                showFrom: [true, false, false, false],
                active:0,
                productForm:Object.assign({},defaultProductParam)
            }
        },
        methods: {
            hidAll(){
                for (let i = 0; i <this.showFrom.length ; i++) {
                   this.showFrom[i]=false
                }
            },
            nextStep(){
                if (this.active<this.showFrom.length-1){
                    this.hidAll();
                    this.active++;
                    this.showFrom[this.active]=true;
                }
            },
            prevStep(){
                this.hidAll();
                this.active--;
                this.showFrom[this.active]=true;
            },
            getUpdateData(){
              if (this.isEdit){
                  queryProductAll(this.$route.query.productId).then(res=>{
                    this.productForm=res.data.data
                  })
              }
            },
        },
        mounted() {
          this.getUpdateData();
        }
    }
</script>

<style scoped>

</style>