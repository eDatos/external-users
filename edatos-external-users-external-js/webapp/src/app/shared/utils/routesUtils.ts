
export function addQueryParamToRoute(route: string, queryParamName:string, queryParamValue: string) {
    var newRoute = route.trim();
    newRoute = newRoute[newRoute.length - 1] === '/' ? newRoute.slice(0, -1) : newRoute;
    newRoute += /\?[^/]+$/.test(newRoute) ? '&' : '?';
    return newRoute + queryParamName + '=' + queryParamValue;
}