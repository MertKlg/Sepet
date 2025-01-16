export const orderCodes = {
    ORDER_CANCELED : "ORDER_CANCELED",
    ORDER_ORDERED : "ORDER_ORDERED",
    ORDER_SHIPPED : "ORDER_SHIPPED",
    ORDER_DELIVERED : "ORDER_DELIVERED",
}


export const verifyOrderCode = (code: string): string | undefined => {
    const normalizedCode = code.toUpperCase();
    const data = Object.values(orderCodes).find(orderCode => orderCode === normalizedCode);
    return data ?? undefined;
};