<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i> 商品分类
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
             <el-button size="small"  @click="addUmsAttrType()">
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
                <el-table-column type="selection" width="100" align="center"></el-table-column>
                <el-table-column prop="id" label="编号" width="100px" align="center"></el-table-column>
                <el-table-column prop="name" label="分类名称"></el-table-column>
                <el-table-column prop="attributeCount" label="属性数量"></el-table-column>
                <el-table-column prop="paramCount" label="参数数量"></el-table-column>
                <el-table-column label="设置" prop="navStatus" >
                    <template slot-scope="scope" width="200px" >
                        <el-button size="mini"   @click="toAttrType(scope.row)">
                            <i class="el-icon-lx-top"></i>属性设置</el-button>
                        <el-button size="mini" @click="toParamSetting(scope.row)">
                            <i class="el-icon-lx-top"></i>参数设置</el-button>
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

      <!--编辑弹出框 -->
        <el-dialog title="分类编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="分类名称">
                    <el-input v-model="form.name"></el-input>
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

    import {queryAttrType,saveAttrType,queryAttributeSettingById} from "../../../api/catepory/attrType";


    export default {

        name: "TableCatepory",
        data() {
            return {
                query: {
                    name: '',
                    current: 1,
                    size: 3
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                form:{
                    id:"",
                  name:""
                },
                editVisible: false,
                parentList:[],
                total: 0,
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
                queryAttrType(this.query).then(res => {
                    this.tableData = res.data.data.records;
                    this.total = res.data.data.total || 50;
                });
            },
            handlePageChange(val){
                this.$set(this.query,"current",val)
                this.getData();
            },
            addUmsAttrType(){
                this.clearAttrType();
                this.editVisible=true
            },
            saveEdit(){
                saveAttrType(this.form).then(res=>{
                  alert(res.data.msg);
                })
                this.editVisible=false
                this.getData();
            },
            clearAttrType(){
                this.form={
                    id:"",
                   name:""
                }
            },
            handleEdit(row){
                this.$set(this.form,"id",row.id)
                this.$set(this.form,"name",row.name)
                this.editVisible=true
            },
            toAttrType(row){
               this.$router.push({path:"/attrSetting",query:{cid:row.id,cname:row.name,type:0,num:Math.floor(Math.random()*100)}});
            },
            toParamSetting(row){
              this.$router.replace({path:"/paramSetting",query:{cid:row.id,cname:row.name,type:1}})

            }

        },
        mounted() {
            this.getData();
        }
    }
</script>

<style scoped>

</style>