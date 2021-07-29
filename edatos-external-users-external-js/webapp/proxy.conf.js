const PROXY_CONFIG = [
    {
        context: ['/api', '/v2/api-docs', '/h2-console', '/logout'],
        target: 'http://localhost:8080',
        secure: false,
    },
];

module.exports = PROXY_CONFIG;
