<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 参数设置
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button size="small"  @click="addAttrSetting()">
                    <i class="el-icon-lx-add"></i>新增</el-button>
                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            </div>
            <el-table
                    :data="tableData"
                    border
                    class="table"
                    ref="multipleTable"
                    header-cell-class-name="table-header"
                    @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="编号" width="50px" align="center"></el-table-column>
                <el-table-column label="分类商品" align="center">
                    <template slot-scope="scope">
                        {{$route.query.cname}}
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="属性名称"></el-table-column>
                <el-table-column label="属性选择类型" align="center">
                    <template slot-scope="scope">
                        {{scope.row.selectType==0?"唯一":scope.row.selectType==1?"单选":"多选"}}
                    </template>
                </el-table-column>
                <el-table-column label="属性录入方式" align="center">
                    <template slot-scope="scope">
                        {{scope.row.inputType==0?"手工录入":"从列表中选取"}}
                    </template>
                </el-table-column>
                <el-table-column prop="inputList" label="可选值列表"></el-table-column>
                <el-table-column prop="sort" label="排序"></el-table-column>
                <el-table-column label="检索类型" align="center">
                    <template slot-scope="scope">
                        {{scope.row.filterType==0?"普通":"颜色"}}
                    </template>
                </el-table-column>
                <el-table-column prop="sort"  label="排序状态"></el-table-column>
                <el-table-column label="操作" width="150px" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                icon="el-icon-edit"
                                @click="handleEdit(scope.row)"
                        >编辑
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                        background
                        layout="total, prev, pager, next"
                        :current-page="query.current"
                        :page-size="query.size"
                        :total="total"
                        @current-change="handlePageChange"
                ></el-pagination>
            </div>

        </div>

        <!-- 编辑弹出框 -->
            <el-dialog title="编辑" :visible.sync="editVisible" width="50%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="属性选择类型">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="商品名称">
                    <el-input v-model="form.typeName"></el-input>
                </el-form-item>
                <el-form-item label="属性选择类型">
                    <el-radio-group v-model="form.selectType">
                        <el-radio :label="0">唯一</el-radio>
                        <el-radio :label="1">单选</el-radio>
                        <el-radio :label="2">多选</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="属性录入方式">
                    <el-radio-group v-model="form.inputType">
                        <el-radio :label="0">手工录入</el-radio>
                        <el-radio :label="1">从列表中选取</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="form.sort"></el-input>
                </el-form-item>
                <el-form-item label="检索类型">
                    <el-radio-group v-model="form.searchType">
                        <el-radio :label="0">不需要进行检索</el-radio>
                        <el-radio :label="1">关键字检索</el-radio>
                        <el-radio :label="2">范围检索</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="分类筛选样式">
                    <el-radio-group v-model="form.filterType">
                        <el-radio :label="0">普通</el-radio>
                        <el-radio :label="1">颜色</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="属性产品是否关联">
                    <el-radio-group v-model="form.relatedStatus">
                        <el-radio :label="0">不关联</el-radio>
                        <el-radio :label="1">关联</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="是否支持手动新增">
                    <el-radio-group v-model="form.handAddStatus">
                        <el-radio :label="0">不支持</el-radio>
                        <el-radio :label="1">支持</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="可选值列表">
                    <el-input
                            type="textarea"
                            autosize
                            placeholder="请输入内容"
                            v-model="inputListFormat">
                    </el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="editVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>

    import {queryAttributeSetting,saveAttributeSetting,updateAttributeSetting,queryAttributeSettingById} from "../../../api/catepory/attrSetting";

    const initAttributeSetting={
        id:null,
        productAttributeCategoryId:null,
        typeName:"",
        name:"",
        selectType:0,
        inputType:0,
        inputList:"",
        sort:null,
        filterType:0,
        searchType:0,
        relatedStatus:0,
        handAddStatus:0,
        type:null,
    }

    export default {
        name: "TableCatepory",
        data() {
            return {
                query: {
                    type:this.$route.query.type,
                    pid:this.$route.query.cid,
                    name: '',
                    current: 1,
                    size: 3
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                inputListFormat:"",
                parentList:[],
                total: 0,
                form:Object.assign({},initAttributeSetting),
                idx: -1,
                id: -1
            }
        },
        created() {
            this.getData();
        },
        watch:{
            inputListFormat:function (newValue,oldValue) {
                newValue=newValue.replace(/\n/g,',');
                this.form.inputList=newValue;
            },
            $route(){
                this.query.pid=this.$route.query.cid
                this.query.type=this.$route.query.type
                this.getData();
            }
        },
        methods: {
            // 获取 easy-mock 的模拟数据
            getData() {
             queryAttributeSetting(this.query).then(res=>{
                this.tableData=res.data.data.records;
                this.total=res.data.data.total
             })
            },
            handlePageChange(val){
              this.$set(this.query,"current",val)
              this.getData();
            },
            addAttrSetting(){
                this.clearAttrSetting();
                this.editVisible=true;
            },
            clearAttrSetting(){
                this.form=Object.assign({},initAttributeSetting);
                this.$set(this.form,"typeName",this.$route.query.cname)
                this.$set(this.form,"type",this.$route.query.type)
                this.$set(this.form,"productAttributeCategoryId",this.$route.query.cid)
                this.inputListFormat="";
            },
            saveEdit(){
                if (this.form.id!==null){
                    updateAttributeSetting(this.form).then(res=>{
                        alert(res.data.msg);
                    })
                }else {
                    saveAttributeSetting(this.form).then(res=>{
                        alert(res.data.msg);
                    })
                }
                this.clearAttrSetting();
                this.editVisible=false;
                this.getData()
            },
            handleEdit(row){
                this.editVisible=true;
                this.clearAttrSetting();
                queryAttributeSettingById(row.id).then(res=>{
                    alert(JSON.stringify(res.data));
                    this.form=res.data.data
                    this.$set(this.form,"typeName",this.$route.query.cname)
                    this.$set(this.form,"type",this.$route.query.type)
                    this.$set(this.form,"productAttributeCategoryId",this.$route.query.cid)
                    let split =this.form.inputList.split(",");
                    for (let i = 0; i <split.length ; i++) {
                        this.inputListFormat+=split[i]+"\n"
                    }


                })
            }
        },
        mounted() {
        }
    }
</script>

<style scoped>

</style>