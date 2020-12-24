<template>
    <div>
        <cate-pory-disclose-main :is-edit="false"></cate-pory-disclose-main>
    </div>
</template>

<script>
    import CatePoryDiscloseMain from "./gropFrom/catePoryDiscloseMain"
    export default {
        name: "add",
        components:{
            CatePoryDiscloseMain
        },
        props:{
            value:Object,
        },
        data() {
            return {}
        },
        methods: {

        },
        mounted() {
        }
    }
</script>

<style scoped>

</style>