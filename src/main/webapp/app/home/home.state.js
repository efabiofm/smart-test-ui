(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/components/login-quenti/login-quenti.html',
                    controller: 'LoginQuentiController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('login');
                    return $translate.refresh();
                }]
            }
        })
        .state('home-quenti', {
            parent: 'app',
            url: '/index',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home-quenti.html'
                    //controller: 'HomeController',
                    //controllerAs: 'vm'
                }
            }
        });
    }
})();
