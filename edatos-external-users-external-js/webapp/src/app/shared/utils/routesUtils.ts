export function addQueryParamToRoute(route: string, queryParamName: string, queryParamValue: string) {
    var url = new URL(route);
    url.searchParams.set(queryParamName, queryParamValue);
    return url.href;
}
