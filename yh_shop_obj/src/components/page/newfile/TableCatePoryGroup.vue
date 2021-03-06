<template> 
    <div class="app-container">
        <el-card class="filter-container" shadow="never">
            <div>
                <i class="el-icon-search"></i>
                <span>筛选搜索</span>
                <el-button
                        style="float: right"
                        @click="handleSearchList()"
                        type="primary"
                        size="small">
                    查询结果
                </el-button>
                <el-button
                        style="float: right;margin-right: 15px"
                        @click="handleResetSearch()"
                        size="small">
                    重置
                </el-button>
            </div>
            <div style="margin-top: 15px">
                <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
                    <el-form-item label="输入搜索：">
                        <el-input style="width: 203px" v-model="listQuery.keyword" placeholder="商品名称"></el-input>
                    </el-form-item>
                    <el-form-item label="商品货号：">
                        <el-input style="width: 203px" v-model="listQuery.productSn" placeholder="商品货号"></el-input>
                    </el-form-item>
                    <el-form-item label="商品分类：">
                        <el-cascader
                                clearable
                                v-model="selectProductCateValue"
                                :options="productCateOptions">
                        </el-cascader>
                    </el-form-item>
                    <el-form-item label="商品品牌：">
                        <el-select v-model="listQuery.brandId" placeholder="请选择品牌" clearable>
                            <el-option
                                    v-for="item in brandOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="上架状态：">
                        <el-select v-model="listQuery.publishStatus" placeholder="全部" clearable>
                            <el-option
                                    v-for="item in publishStatusOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="审核状态：">
                        <el-select v-model="listQuery.verifyStatus" placeholder="全部" clearable>
                            <el-option
                                    v-for="item in verifyStatusOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-form>
            </div>
        </el-card>
        <el-card class="operate-container" shadow="never">
            <i class="el-icon-tickets"></i>
            <span>数据列表</span>
            <el-button
                    class="btn-add"
                    @click="handleAddProduct()"
                    size="mini">
                添加
            </el-button>
        </el-card>
        <div class="table-container">
            <el-table ref="productTable"
                      :data="list"
                      style="width: 100%"
                      @selection-change="handleSelectionChange"
                      v-loading="listLoading"
                      border>
                <el-table-column type="selection" width="60" align="center"></el-table-column>
                <el-table-column label="编号" width="100" align="center">
                    <template slot-scope="scope">{{scope.row.id}}</template>
                </el-table-column>
                <el-table-column label="商品图片" width="120" align="center">
                    <template slot-scope="scope"><img style="height: 80px" :src="scope.row.pic"></template>
                </el-table-column>
                <el-table-column label="商品名称" align="center">
                    <template slot-scope="scope">
                        <p>{{scope.row.name}}</p>
                        <p>品牌：{{scope.row.brandName}}</p>
                    </template>
                </el-table-column>
                <el-table-column label="价格/货号" width="120" align="center">
                    <template slot-scope="scope">
                        <p>价格：￥{{scope.row.price}}</p>
                        <p>货号：{{scope.row.productSn}}</p>
                    </template>
                </el-table-column>
                <el-table-column label="标签" width="140" align="center">
                    <template slot-scope="scope">
                        <p>上架：
                            <el-switch
                                    @change="handlePublishStatusChange(scope.$index, scope.row)"
                                    :active-value="1"
                                    :inactive-value="0"
                                    v-model="scope.row.publishStatus">
                            </el-switch>
                        </p>
                        <p>新品：
                            <el-switch
                                    @change="handleNewStatusChange(scope.$index, scope.row)"
                                    :active-value="1"
                                    :inactive-value="0"
                                    v-model="scope.row.newStatus">
                            </el-switch>
                        </p>
                        <p>推荐：
                            <el-switch
                                    @change="handleRecommendStatusChange(scope.$index, scope.row)"
                                    :active-value="1"
                                    :inactive-value="0"
                                    v-model="scope.row.recommandStatus">
                            </el-switch>
                        </p>
                    </template>
                </el-table-column>
                <el-table-column label="排序" width="100" align="center">
                    <template slot-scope="scope">{{scope.row.sort}}</template>
                </el-table-column>
                <el-table-column label="SKU库存" width="100" align="center">
                    <template slot-scope="scope">
                        <el-button type="primary" icon="el-icon-edit" @click="handleShowSkuEditDialog(scope.$index, scope.row)" circle></el-button>
                    </template>
                </el-table-column>
                <el-table-column label="销量" width="100" align="center">
                    <template slot-scope="scope">{{scope.row.sale}}</template>
                </el-table-column>
                <el-table-column label="审核状态" width="100" align="center">
                    <template slot-scope="scope">
                        <p>{{scope.row.verifyStatus | verifyStatusFilter}}</p>
                        <p>
                            <el-button
                                    type="text"
                                    @click="handleShowVerifyDetail(scope.$index, scope.row)">审核详情
                            </el-button>
                        </p>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="160" align="center">
                    <template slot-scope="scope">
                        <p>
                            <el-button
                                    size="mini"
                                    @click="handleShowProduct(scope.$index, scope.row)">查看
                            </el-button>
                            <el-button
                                    size="mini"
                                    @click="handleUpdateProduct(scope.row)">编辑
                            </el-button>
                        </p>
                        <p>
                            <el-button
                                    size="mini"
                                    @click="handleShowLog(scope.$index, scope.row)">日志
                            </el-button>
                            <el-button
                                    size="mini"
                                    type="danger"
                                    @click="handleDelete(scope.$index, scope.row)">删除
                            </el-button>
                        </p>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="batch-operate-container">
            <el-select
                    size="small"
                    v-model="operateType" placeholder="批量操作">
                <el-option
                        v-for="item in operates"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
            <el-button
                    style="margin-left: 20px"
                    class="search-button"
                    @click="handleBatchOperate()"
                    type="primary"
                    size="small">
                确定
            </el-button>
        </div>
        <div class="pagination-container">
            <el-pagination
                    background
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    layout="total, sizes,prev, pager, next,jumper"
                    :page-size="listQuery.size"
                    :page-sizes="[5,10,15]"
                    :current-page.sync="listQuery.current"
                    :total="total">
            </el-pagination>
        </div>
        <el-dialog
                title="编辑货品信息"
                :visible.sync="editSkuInfo.dialogVisible"
                width="40%">
            <span>商品货号：</span>
            <span>{{editSkuInfo.productSn}}</span>
            <el-input placeholder="按sku编号搜索" v-model="editSkuInfo.keyword" size="small" style="width: 50%;margin-left: 20px">
                <el-button slot="append" icon="el-icon-search" @click="handleSearchEditSku"></el-button>
            </el-input>
            <el-table style="width: 100%;margin-top: 20px"
                      :data="editSkuInfo.stockList"
                      border>
                <el-table-column
                        label="SKU编号"
                        align="center">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.skuCode"></el-input>
                    </template>
                </el-table-column>
                <el-table-column
                        v-for="(item,index) in editSkuInfo.productAttr"
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
                        <el-input v-model="scope.row.price"></el-input>
                    </template>
                </el-table-column>
                <el-table-column
                        label="商品库存"
                        width="80"
                        align="center">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.stock"></el-input>
                    </template>
                </el-table-column>
                <el-table-column
                        label="库存预警值"
                        width="100"
                        align="center">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.lowStock"></el-input>
                    </template>
                </el-table-column>
            </el-table>
            <span slot="footer" class="dialog-footer">
        <el-button @click="editSkuInfo.dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEditSkuConfirm">确 定</el-button>
      </span>
        </el-dialog>
    </div>
</template>
<script>

import {queryCatePoryGrop} from "../../../api/catepory/catePoryGroup";


const defaultListQuery = {
        keyword: null,
        current: 1,
        size: 5,
        publishStatus: null,
        verifyStatus: null,
        productSn: null,
        productCategoryId: null,
        brandId: null
    };
    export default {
        name: "tableCatePoryGroup",
        data() {
            return {
                editSkuInfo:{
                    dialogVisible:false,
                    productId:null,
                    productSn:'',
                    productAttributeCategoryId:null,
                    stockList:[],
                    productAttr:[],
                    keyword:null
                },
                operateType: null,
                listQuery: Object.assign({}, defaultListQuery),
                list: null,
                total: null,
                listLoading: false,
                selectProductCateValue: null,
                multipleSelection: [],
                productCateOptions: [],
                brandOptions: [],
                publishStatusOptions: [{
                    value: 1,
                    label: '上架'
                }, {
                    value: 0,
                    label: '下架'
                }],
                verifyStatusOptions: [{
                    value: 1,
                    label: '审核通过'
                }, {
                    value: 0,
                    label: '未审核'
                }]
            }
        },
        created() {
         this.getData();
        },
        watch: {
            selectProductCateValue: function (newValue) {
                if (newValue != null && newValue.length == 2) {
                    this.listQuery.productCategoryId = newValue[1];
                } else {
                    this.listQuery.productCategoryId = null;
                }

            }
        },
        filters: {
            verifyStatusFilter(value) {
                if (value === 1) {
                    return '审核通过';
                } else {
                    return '未审核';
                }
            }
        },

        methods: {
            getData(){
                queryCatePoryGrop(this.listQuery).then(res=>{
                    this.list=res.data.data.records;
                    this.total=res.data.data.total
                })
            },
            handleSizeChange(val) {
                this.listQuery.current = 1;
                this.listQuery.size = val;
                this.getData();
            },
            handleCurrentChange(val) {
                this.listQuery.current = val;
                this.getData();
            },
            handleUpdateProduct(row){
                this.$router.push({path:"/updatePory",query:{productId:row.id}});
            },
            handleAddProduct(){
                this.$router.push("/addPory");
            }
        }
    }
</script>
<style></style>


