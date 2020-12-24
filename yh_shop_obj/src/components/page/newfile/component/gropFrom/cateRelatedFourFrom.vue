<template>
<div>
    <h1>商品关联信息</h1>
    <el-form>
        <el-button style="margin-top: 12px;" @click="handlePrev">上一步</el-button>
        <el-button style="margin-top: 12px;"  @click="submitCatePory()" >提交</el-button>
    </el-form>
</div>


</template>

<script>
    import {saveProduct} from "../../../../../api/catepory/product";

    export default {
        name: "cateMassageOneFrom",
        data() {
            return {}
        },
        props: {
            value: Object,
            isEdit: {
                type: Boolean,
                default: false
            }
        },
        methods: {
            handlePrev(){
                this.$emit("prevStep")
            },
            submitCatePory(){
                alert(JSON.stringify(this.value));
                saveProduct(this.value).then(res=>{
                    if (res.data.code===110){
                        alert(res.data.code)
                        this.$router.push("/catePoryGroup")
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