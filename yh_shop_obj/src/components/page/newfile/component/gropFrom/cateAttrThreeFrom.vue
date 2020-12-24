<template>
    <div>
        <div style="margin-top: 50px">
            <el-form :model="value" label-width="120px" style="width: 900px;margin: 0 auto;" size="small">
                <el-form-item label="属性类型：">
                    <el-select v-model="value.productAttributeCategoryId"
                               placeholder="请选择属性类型"
                               @change="handleProductAttrChange">
                        <el-option
                                v-for="item in productAttributeCategoryOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="商品规格：">
                    <el-card shadow="never" class="cardBg">
                        <div v-for="(productAttr,idx) in selectProductAttr">
                            {{productAttr.name}}：
                            <el-checkbox-group v-if="productAttr.handAddStatus===0" v-model="selectProductAttr[idx].values">
                                <el-checkbox v-for="item in getInputListArr(productAttr.inputList)" :label="item" :key="item"
                                             class="littleMarginLeft"></el-checkbox>
                            </el-checkbox-group>
                            <div v-else>
                                <el-checkbox-group v-model="selectProductAttr[idx].values">
                                    <div v-for="(item,index) in selectProductAttr[idx].options" style="display: inline-block"
                                         class="littleMarginLeft">
                                        <el-checkbox :label="item" :key="item"></el-checkbox>
                                        <el-button type="text" class="littleMarginLeft" @click="handleRemoveProductAttrValue(idx,index)">删除
                                        </el-button>
                                    </div>
                                </el-checkbox-group>
                                <el-input v-model.trim="addProductAttrValue " style="width: 160px;margin-left: 10px" clearable></el-input>
                                <el-button class="littleMarginLeft" @click="handleAddProductAttrValue(idx)">增加</el-button>
                            </div>
                        </div>
                    </el-card>
                    <el-table style="width:900px;margin-top: 20px"
                              :data="value.skuStockList"
                              border>
                        <el-table-column
                                v-for="(item,index) in selectProductAttr"
                                :label="item.name"
                                :key="item.id"
                                align="center">
                            <template slot-scope="scope">
                                {{getProductSkuSp(scope.row,index)}}
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="销售价格"
                                width="80"
                                align="center">
                            <template slot-scope="scope">
                                <el-input v-on:input="priceChange" v-model="scope.row.price"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="商品库存"
                                width="80"
                                align="center">
                            <template slot-scope="scope">
                                <el-input  v-on:input="priceStock" v-model="scope.row.stock"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="库存预警值"
                                width="80"
                                align="center">
                            <template slot-scope="scope">
                                <el-input v-on:input="priceLowStock" v-model="scope.row.lowStock"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="SKU编号"
                                width="80"
                                align="center">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.skuCode"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column
                                label="商品图片"
                                align="center">
                            <template slot-scope="scope" >
                               <file-upload v-model="scope.row.pic"></file-upload>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-button
                            type="primary"
                            style="margin-top: 20px"
                            @click="handleRefreshProductSkuList">刷新列表
                    </el-button>
                    &nbsp; &nbsp; &nbsp;
                    同步价格<el-switch
                            v-model="changeData"
                            active-color="#13ce66"
                            inactive-color="black">
                    </el-switch>
                    &nbsp; &nbsp; &nbsp;
                    同步库存<el-switch
                        v-model="changeStock"
                        active-color="#13ce66"
                        inactive-color="black">
                </el-switch>
                </el-form-item>
                <el-form-item label="属性图片:" v-if="hasAttrPic">
                    <el-card shadow="never" class="cardBg">
                        <div v-for="(item,index) in selectProductAttrPics">
                            <span>{{item.name}}:</span>
                            <single-upload v-model="item.pic"
                                           style="width: 300px;display: inline-block;margin-left: 10px"></single-upload>
                        </div>
                    </el-card>
                </el-form-item>
                <el-form-item label="商品参数：">
                    <el-card shadow="never" class="cardBg">
                        <div v-for="(item,index) in selectProductParam" :class="{littleMarginTop:index!==0}">
                            <div class="paramInputLabel">{{item.name}}:</div>
                            <el-select v-if="item.inputType===1" class="paramInput"
                                       v-model="selectProductParam[index].value">
                                <el-option
                                        v-for="item in getParamInputList(item.inputList)"
                                        :key="item"
                                        :label="item"
                                        :value="item">
                                </el-option>
                            </el-select>
                            <el-input v-else class="paramInput"  v-model="selectProductParam[index].value"></el-input>
                        </div>
                    </el-card>
                </el-form-item>
                <el-form-item label="商品相册：">
                    <multi-upload  v-model="selectProductPics"></multi-upload>
                </el-form-item>
                <el-form-item label="规格参数：">
                    <el-tabs v-model="activeHtmlName" type="card">
                        <el-tab-pane label="电脑端详情" name="pc">
                            <quill-editor :width="595" :height="300" ref="text" v-model="value.detailHtml" class="myQuillEditor" :options="editorOption" />
                        </el-tab-pane>
                        <el-tab-pane label="移动端详情" name="mobile">
                            <quill-editor :width="595" :height="300" ref="text" v-model="value.detailMobileHtml" class="myQuillEditor" :options="editorOption" />
                        </el-tab-pane>
                    </el-tabs>
                </el-form-item>
                <el-form-item style="text-align: center">
                    <el-button size="medium" @click="handlePrev">上一步，填写商品促销</el-button>
                    <el-button type="primary" size="medium" @click="handleNext">下一步，选择商品关联</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import 'quill/dist/quill.core.css'
    import 'quill/dist/quill.snow.css'
    import 'quill/dist/quill.bubble.css'
    import {queryAttrType} from "../../../../../api/catepory/attrType";
    import {queryAttributeSetting} from "../../../../../api/catepory/attrSetting";
    import MultiUpload from "../../../newfileUtils/multiUpload";
    import FileUpload from "../../../newfileUtils/FileUpload";
    import { quillEditor } from 'vue-quill-editor'
    export default {
        name: "cateAttrThreeFrom",
        props: {
            value: Object,
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        computed:{
            productId(){
                return this.value.id
            }
        },
        components:{
            MultiUpload,FileUpload,quillEditor
        },
        watch:{
            productId:function (newValue) {
             if (this.isEdit){
                 this.getSelectProductPics();
               this.handleProductAttrChange(this.value.productAttributeCategoryId);
             }
            }
         },
        data() {
            return {
                dialogVisible: false,
                hasAttrPic:[],
                editorOption: {},
                productAttributeCategoryOptions: [],
                selectProductAttr: [],
                addProductAttrValue: null,
                selectProductParam: [],
                selectProductAttrPics: [],
                selectProductPics: [],
                activeHtmlName: "",
                changeData:false,
                changeStock:false,
            }
        },

        methods: {
            handleNext() {
                //商品属性数据合并
                this.getpmsProductAttributeList();
                this.getAlbumPicsStr();
                this.$emit("nextStep")
            },
            handlePrev() {
                this.$emit("prevStep")
            },
            handlDatahuixian(list){
                let value=""
                var pmsProductAttributeList = this.value.pmsProductAttributeList;
                    for (let j = 0; j <pmsProductAttributeList.length ; j++) {

                       if (list.id===pmsProductAttributeList[j].productAttributeId){

                           value=pmsProductAttributeList[j].value

                       }

                }
                return value;
            },
            getpmsProductAttributeList(){
                //先清空防止后边进来有值
                this.value.pmsProductAttributeList=[];
                for (let i = 0; i<this.selectProductAttr.length ; i++) {
                    var attr = this.selectProductAttr[i];
                    //判断已选中处于编辑模式 并且添加啦值
                    if (attr.handAddStatus===1 && attr.options!==null && attr.options.length>0 ){
                        this.value.pmsProductAttributeList.push({
                            productAttributeId:attr.id,
                            value:this.getOptionValue(attr.options)
                        })
                    }
                }
                for (let i = 0; i <this.selectProductParam.length ; i++) {
                    var attr = this.selectProductParam[i];
                    alert(i+":"+JSON.stringify(this.selectProductParam[i]));
                    //判断已选中处于编辑模式 并且添加啦值
                        this.value.pmsProductAttributeList.push({
                            productAttributeId:attr.id,
                            value:attr.value
                        })
                    }
            },
            //
            getSelectProductPics(){
            this.selectProductPics=this.value.albumPics.split(",");
            this.value.pic=this.value.albumPics.split(",")[0];
            },
            getOptionValue(option){
                let value="";
                for (let i = 0; i <option.length; i++) {
                    if(i===option.length-1){
                        value+=option[i]
                    }else{
                        value+=option[i]+",";
                    }
                }
                return value;
            },
            //商品相册
            getAlbumPicsStr(){
                this.value.albumPics='';
                var list = this.selectProductPics;
                if (list.length>0 && list!=null){
                    let photos="";
                    for (let i = 0; i <list.length ; i++) {
                        if (i===0){
                            this.value.pic=list[i];
                        }
                        if (i===list.length-1){
                            photos+=list[i];
                        }else{
                            photos+=list[i]+',';
                        }
                    }
                    if(photos!==''){
                        this.value.albumPics=photos;
                    }else{
                        this.value.albumPics='';
                    }
                }
            },
            priceChange(val){
                if (this.changeData===true){
                    for (let i = 0; i <this.value.skuStockList.length; i++) {
                        this.value.skuStockList[i].price=val;
                    }
                }
            },
            priceStock(val){
                if (this.changeStock===true){
                    for (let i = 0; i <this.value.skuStockList.length; i++) {
                        this.value.skuStockList[i].stock=val;
                    }
                }
            },
            priceLowStock(val){
                if (this.changeStock===true){
                    for (let i = 0; i <this.value.skuStockList.length; i++) {
                        this.value.skuStockList[i].lowStock=val;
                    }
                }
            },
            //刷新表格的方
            handleRefreshProductSkuList(){
                this.$confirm('此操作suk操作 慎重', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消操作'
                    });
                    return false;
                }).then(() => {
                    this.value.skuStockList=[];
                    let skuList=this.value.skuStockList;
                    let length = this.selectProductAttr.length;
                    if (length===1){
                        let attr = this.selectProductAttr[0];
                        for (let i = 0; i <attr.values.length; i++) {
                            skuList.push({
                                spData:JSON.stringify([{key:attr.name,value:attr.values[i]}])
                            })
                        }
                    }else if (length===2){
                        let attr = this.selectProductAttr[0];
                        let attr1 = this.selectProductAttr[1];
                        for (let i = 0; i <attr.values.length; i++) {
                            if(attr1.values.length === 0){
                                this.$message({
                                    message: '属性数据不完整。',
                                    type: 'warning',
                                    duration: 1000
                                });
                                break;
                            }
                            for (let j = 0; j <attr1.values.length ; j++) {
                                let spaData=[];
                                spaData.push({key:attr.name,value:attr.values[i]})
                                spaData.push({key:attr1.name,value:attr1.values[j]})
                                skuList.push({
                                    spData:JSON.stringify(spaData)
                                })
                            }
                        }
                    }else if (length===3){
                        let attr = this.selectProductAttr[0];
                        let attr1 = this.selectProductAttr[1];
                        let attr2 = this.selectProductAttr[2];
                        for (let i = 0; i <attr.values.length; i++) {
                            if(attr1.values.length === 0){
                                this.$message({
                                    message: '属性数据不完整。',
                                    type: 'warning',
                                    duration: 1000
                                });
                                break;
                            }
                            for (let j = 0; j <attr1.values.length ; j++) {
                                for (let k = 0; k <attr2.values.length; k++) {
                                    let spaData=[];
                                    spaData.push({key:attr.name,value:attr.values[i]})
                                    spaData.push({key:attr1.name,value:attr1.values[j]})
                                    spaData.push({key:attr2.name,value:attr2.values[k]})
                                    skuList.push({
                                        spData:JSON.stringify(spaData)
                                    })
                                }

                            }
                        }
                    }
                })

            },
            getProductSkuSp(row,index){
                //现将转为数组在进行下标的寻找
                let spData=JSON.parse(row.spData);
                if(spData != null && index<spData.length){
                    return spData[index].value;
                }
                return null;
            },
            getInputListArr(strAttr){
                if (strAttr.length>0){
                    let split = strAttr.split(",");
                        return split;
                }
            },
            //手动属性设置的增加方法
            handleAddProductAttrValue(idx){
                if (this.selectProductAttr[idx].options.length>=1){
                  let list=this.selectProductAttr[idx].options;
                    for (let i = 0; i <list.length ; i++) {
                         if (list[i]===this.addProductAttrValue){
                             this.$message.error("你输入的值不符合定义");
                             return;
                         }
                    }
                }
                if (this.addProductAttrValue!==undefined && this.addProductAttrValue!==null&& this.addProductAttrValue!==''){
                    this.selectProductAttr[idx].options.push(this.addProductAttrValue)
                }else{
                    this.$message.error("你输入的值不符合定义");
                }
            },
            //删除手动添加的属性
            handleRemoveProductAttrValue(idx,index){
                this.selectProductAttr[idx].options.splice(index,1)
                var values = this.selectProductAttr[idx].values;
                for (let i = 0; i <values.length; i++) {
                    if (values[i]===this.selectProductAttr[idx].values[index]){
                        values.splice(i,1)
                    }
                }
                this.selectProductAttr[idx].values=values;

            },
            getParamInputList(strParam) {
                let split = strParam.split(",");
                return split;
            },
            //根据商品分类表中的数据 来回显商品属性的数据
            handleProductAttrChange(val) {
                if (val!==null && val!==""){
                    this.queryAttributeSetting(val)
                    this.queryParamibuteSetting(val)
                }
            },
            queryParamibuteSetting(val){
                this.selectProductParam=[];
                //选中事键改变清空表格数据
                let query = {type:1,pid:val,current:1,size: 15}
                let value="";
                queryAttributeSetting(query).then(res=>{
                    let list=res.data.data.records;
                    if (list.length>0){
                        for (let i = 0; i <list.length; i++) {
                            if (this.isEdit){
                                value = this.handlDatahuixian(list[i]);
                            }
                            this.selectProductParam.push({
                                value:value,
                                inputList:list[i].inputList,
                                id:list[i].id,
                                name:list[i].name,
                                inputType:list[i].inputType,
                                });
                        }
                    }

                    if (list.length==0) {
                        this.$message.error("请调整你的参数状态")
                    }

                })
            },
            queryAttributeSetting(val){
                //选中事键改变清空表格数据
                let query = {type:0,pid:val,current:1,size: 15}
                let values=[];
                let options=[];
                this.selectProductAttr=[];
                queryAttributeSetting(query).then(res=>{
                    let list=res.data.data.records;
                    if(this.isEdit){
                        values=this.getProductAttrValues(list)
                        options=this.getProductAttrOptions(list);
                    }
                    if (list.length>0){
                        for (let i = 0; i <list.length; i++) {
                            this.selectProductAttr.push({
                                values:values,
                                inputList:list[i].inputList,
                                id:list[i].id,
                                name:list[i].name,
                                handAddStatus:list[i].handAddStatus,
                                options:options});
                        }
                    }
                    if (list.length===0) {
                        this.$message.error("请调整你的属性状态")
                    }
                })
            },
            getProductAttrValues(list){

                let set=new Set();
                var skuStockList = this.value.skuStockList;
                //声明一个Set集合
                for (let i = 0; i <list.length ; i++) {
                    for (let j = 0; j <skuStockList.length;j++) {
                        var spData = JSON.parse(skuStockList[j].spData);
                        for (let k = 0; k <spData.length ; k++) {
                            if (list[i].name===spData[k].key){
                                set.add(spData[k].value)
                                break;
                            }
                        }

                    }
                }
                return Array.from(set);
            },
            getProductAttrOptions(list){
                var message = this.value.pmsProductAttributeList;
                let options=[];
                for (let i = 0; i <list.length ; i++) {
                    for (let j = 0; j <message.length ; j++) {
                        if (list[i].id===message[j].productAttributeId){
                            options.push(message[j].value);
                        }
                    }
                }

                return options;
            },
            //查询商品分类
            queryCatePoryAttr() {
                let query = {current: 1, size: 15}
                queryAttrType(query).then(res => {
                    let list = res.data.data.records;
                    for (var i = 0; i < list.length; i++) {
                        this.productAttributeCategoryOptions.push({value: list[i].id, label: list[i].name})
                    }
                });
            }

        },
        mounted() {
            this.queryCatePoryAttr();

        }
    }
</script>

<style>

    .littleMarginLeft {
        margin-left: 10px;
    }

    .littleMarginTop {
        margin-top: 10px;
    }

    .paramInput {
        width: 250px;
    }

    .paramInputLabel {
        display: inline-block;
        width: 100px;
        text-align: right;
        padding-right: 10px
    }

    .cardBg {
        background: #F8F9FC;
    }
</style>