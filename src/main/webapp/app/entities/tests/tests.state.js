(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('tests', {
            parent: 'admin',
            url: '/tests',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'tests.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tests/tests.html',
                    controller: 'JhiTestsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tests');
                    return $translate.refresh();
                }]
            },
            onEnter: ['JhiTestsService', function(JhiTestsService) {
                // JhiTestsService.subscribe();
            }],
            onExit: ['JhiTestsService', function(JhiTestsService) {
                //JhiTestsService.unsubscribe();
            }]
        });
    }
})();
