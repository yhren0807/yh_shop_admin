<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 商品表格
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button size="small"  @click="addUmsCategrop()">
                    <i class="el-icon-lx-add"></i>新增</el-button>
                <el-button
                        type="primary"
                        icon="el-icon-delete"
                        class="handle-del mr10"
                        @click="delAllSelection"
                >批量删除
                </el-button>

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
                <el-table-column prop="id" label="编号" width="30px" align="center"></el-table-column>
                <el-table-column prop="name" label="商品名称"></el-table-column>
                <el-table-column label="商品log" align="center">
                    <template slot-scope="scope">
                        <el-image
                                class="table-td-thumb"
                                :src="scope.row.icon"
                                :preview-src-list="[scope.row.icon]"
                        ></el-image>
                    </template>
                </el-table-column>
                <el-table-column label="商品级别" align="center">
                    <template slot-scope="scope">
                        {{scope.row.level==0?"一级":"二级"}}
                    </template>
                </el-table-column>
                <el-table-column prop="productCount" label="数量"></el-table-column>
                <el-table-column prop="productUnit" label="单位"></el-table-column>
                <el-table-column label="按钮" prop="navStatus">
                    <template slot-scope="scope">
                        <el-switch
                       :active-value="1"
                       :inactive-value="0"
                       v-model="scope.row.navStatus"
                                >
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="显示状态" prop="showStatus">
                    <template slot-scope="scope">
                        <el-switch
                                :active-value="1"
                                :inactive-value="0"
                                v-model="scope.row.showStatus"
                        >
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column prop="description" label="描述"></el-table-column>
                <el-table-column prop="sort"  label="排序状态"></el-table-column>
                <el-table-column label="导航栏状态" prop="navStatus">
                    <template slot-scope="scope">
                        <el-button size="mini"  v-if="scope.row.level==0"  @click="displaySelect(scope.row)">
                            <i class="el-icon-lx-down"></i>显示下级菜单</el-button>
                        <el-button size="mini" v-if="scope.row.level==1"  @click="displayOutSelect(scope.row)">
                            <i class="el-icon-lx-top"></i>显示上级菜单</el-button>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="150px" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                icon="el-icon-edit"
                                @click="handleEdit(scope.row)"
                        >编辑
                        </el-button>
                        <el-button
                                type="text"
                                icon="el-icon-delete"
                                class="red"
                                @click="handleDelete(scope.row)"
                        >删除
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
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="分类名称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="数量">
                    <el-input v-model="form.productCount"></el-input>
                </el-form-item>
                <el-form-item label="数量单位">
                    <el-input v-model="form.productUnit"></el-input>
                </el-form-item>
                <el-form-item label="排序">
                    <el-input v-model="form.sort"></el-input>
                </el-form-item>
                <el-form-item label="上级分类">
                    <el-select v-model="form.parentId" prop="parentId" placeholder="请选择">
                        <el-option label="无上级目录" :value="0" ></el-option>
                        <el-option
                                v-for="item in parentList"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否显示导航栏">
                    <el-radio-group v-model="form.navStatus">
                        <el-radio :label="1">是</el-radio>
                        <el-radio :label="2">否</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="是否显示">
                <el-radio-group v-model="form.showStatus">
                    <el-radio :label="1">是</el-radio>
                    <el-radio :label="2">否</el-radio>
                </el-radio-group>
            </el-form-item>
                <el-form-item label="分类图片">
                   <file-upload v-model="form.icon"></file-upload>
             </el-form-item>
                <el-form-item label="关键字">
                    <el-input v-model="form.keywords"></el-input>
                </el-form-item>
                <el-form-item label="描述">
                    <el-input v-model="form.description"></el-input>
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
    import {queryCategory,queryParent,saveCategory,queryById,updateCategory} from '../../../api/catepory/catePory.js'
    import FileUpload from '../newfileUtils/FileUpload'
    export default {
        name: "TableCatepory",
        components:{
            FileUpload
        },
        data() {
            return {
                query: {
                    parentId:null,
                    levelVo:0,
                    name: '',
                    current: 1,
                    size: 3
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                parentList:[],
                total: 0,
                form: {
                    id:null,
                    name:"",
                    level:"",
                 productCount:"",
                  sort:null,
                    parentId:0,
                  navStatus:1,
                  showStatus:1,
                  icon:"",
                  keywords:"",
                  description:"",
                    productUnit:""
                },
                idx: -1,
                id: -1
            }
        },
        created() {
            this.getData();
        },
        methods: {
            // 获取 easy-mock 的模拟数据
            getData() {
                queryCategory(this.query).then(res => {
                    this.tableData = res.data.data.records;
                    this.total = res.data.data.total || 50;
                });
            },
            clearFrom(){
                this.form={
                    id:null,
                    name:"",
                    level:"",
                    productCount:"",
                    sort:null,
                    parentId:0,
                    navStatus:1,
                    showStatus:1,
                    icon:"",
                    keywords:"",
                    description:"",
                    productUnit:""
                }
            },
            handlePageChange(val){
               this.$set(this.query,"current",val)
                this.getData();
            },
            displaySelect(row){
                this.$set(this.form,"parentId",row.id)
                this.$set(this.query,"parentId",row.id)
                this.$set(this.query,"levelVo",1)
                this.$set(this.query,"current",1)
                this.getData();
            },
            displayOutSelect(){
                this.$set(this.form,"parentId",0)
                this.$set(this.query,"parentId",0)
                this.$set(this.query,"levelVo",0)
                this.$set(this.query,"current",1)
                this.getData();
            },
            queryParents(){
                queryParent().then(res=>{
                    this.parentList=res.data.data
                })
            },
            // 编辑操作
            handleEdit(index, row) {
                this.idx = index;
                this.form = row;
                this.editVisible = true;
            },
            addUmsCategrop(){
                this.queryParents();
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                if (this.form.id!==null){
                    updateCategory(this.form).then(res=>{
                        alert(res.data.msg)
                    })
                } else {
                    if (this.form.parentId===0){
                        this.$set(this.form,"level",0)
                    }else{
                        this.$set(this.form,"level",1)
                    }
                    saveCategory(this.form).then(res=>{
                        alert(res.data.msg)
                    })
                }
                this.getData();
                this.clearFrom();
                this.editVisible = false;
            },
            //修改
            handleEdit(row){
                this.clearFrom();
                this.queryParents();
                queryById(row.id).then(res=>{
                    this.editVisible = true;
                    this.form=res.data.data
                })

            }
        },
        mounted() {
        }
    }
</script>

<style scoped>

</style>