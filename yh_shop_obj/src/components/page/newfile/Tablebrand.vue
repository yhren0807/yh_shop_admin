<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>商品分类
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button
                        type="primary"
                        icon="el-icon-delete"
                        class="handle-del mr10"
                        @click="delAllSelection"
                >批量删除</el-button>
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
                <el-table-column prop="id" label="编号" width="55" align="center"></el-table-column>
                <el-table-column prop="brandName" label="用户名"></el-table-column>
                <el-table-column prop="brandId" label="品牌类型"></el-table-column>
                <el-table-column prop="recommendStatus" label="品牌状态"></el-table-column>
                <el-table-column prop="sort" label="排序"></el-table-column>
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">
                        <el-button
                                type="text"
                                icon="el-icon-edit"
                                @click="handleEdit(scope.$index, scope.row)"
                        >编辑</el-button>
                        <el-button
                                type="text"
                                icon="el-icon-delete"
                                class="red"
                                @click="handleDelete(scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination block" >
                <el-pagination
                        background
                        layout="total, prev, pager, next"
                        :page-sizes="[3, 4, 5, 6]"
                        :current-page="query.size"
                        :page-size="query.limit"
                        :total="count"
                        @current-change="handlePageChange"
                ></el-pagination>
            </div>
        </div>

        <!-- 编辑弹出框 -->
        <el-dialog title="编辑" :visible.sync="editVisible" width="30%">
            <el-form ref="form" :model="form" label-width="70px">
                <el-form-item label="编号">
                    <el-input v-model="form.id" disabled></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="form.brandName"></el-input>
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
    import { fetchBrandData } from '../../../api/brand/brand';
    import { deleteBrandData } from '../../../api/brand/deletebrand';
    import { updateBrandData } from '../../../api/brand/updateBrand';

    export default {
        name: "tablebrand",
        data() {
            return {
                query: {
                    name: '',
                    page: 1,
                    limit: 2
                },
                tableData: [],
                multipleSelection: [],
                delList: [],
                editVisible: false,
                count: 0,
                form: {},
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
                fetchBrandData(this.query).then(res => {
                    this.tableData = res.data.data.data;
                    this.count = res.data.data.count || 50;
                });
            },
            handleDelete(row){
                deleteBrandData(row.id).then(res=>{
                    alert(res.data.msg);
                    this.getData();
                })
            },
            // 编辑操作
            handleEdit(index, row) {
                this.idx = index;
                this.form = row;
                this.editVisible = true;
            },
            // 保存编辑
            saveEdit() {
                updateBrandData(this.form).then(res=>{
                    this.getData();
                    this.$message.success(`修改第 ${this.idx + 1} 行成功`);
                })
                this.editVisible = false;
            },
            handlePageChange(val){
               this.$set(this.query,"page",val)
                this.getData();
            }

        },
        mounted() {
        }
    }
</script>

<style>
    .handle-box {
        margin-bottom: 20px;
    }

    .handle-select {
        width: 120px;
    }

    .handle-input {
        width: 300px;
        display: inline-block;
    }
    .table {
        width: 100%;
        font-size: 14px;
    }
    .red {
        color: #ff0000;
    }
    .mr10 {
        margin-right: 10px;
    }
    .table-td-thumb {
        display: block;
        margin: auto;
        width: 40px;
        height: 40px;
    }
</style>