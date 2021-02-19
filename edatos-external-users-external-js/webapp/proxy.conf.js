const PROXY_CONFIG = [
    {
        context: [
            '/api',
            '/management',
            '/v2/api-docs',
            '/h2-console',
           // '/login/cas', // TODO EDATOS-3266
           // '/logout' // TODO EDATOS-3266
        ],
        target: "http://localhost:8080",
        secure: false
    }
]

module.exports = PROXY_CONFIG;