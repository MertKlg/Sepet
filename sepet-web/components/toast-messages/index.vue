<template>
    <div class="position-fixed bottom-0 end-0 m-5 z-3 toast-messages" v-if="props.messages">

        <div class="toast d-block my-3" role="alert" aria-live="assertive" aria-atomic="true"
            v-for="(toastMessage,index) in props.messages" :key="index">
            <div class="toast-header">
                <strong class="me-auto">Sepet</strong>
                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"
                    @click="closeToast(toastMessage)"></button>
            </div>
            <div class="toast-body">
                {{ toastMessage?.message }}
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import type { IResponse } from '~/model/interface/IResponse';
import { watch } from 'vue';

const props = defineProps<{messages : IResponse[]}>()
const emit = defineEmits(["closeToast"])


watch(props.messages , async() => {
    processMessageQueue()
})

const processMessageQueue = () => {
    if(!props.messages) return

    setTimeout(() => {
        props.messages.forEach(e => closeToast(e))
    }, 5000)
}

const closeToast = (iresponse : IResponse | null | undefined) => {
    if(!iresponse) return
    
    emit("closeToast", iresponse)
}
</script>