(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('plan-prueba', {
            parent: 'entity',
            url: '/plan-prueba',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.planPrueba.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plan-prueba/plan-pruebas.html',
                    controller: 'PlanPruebaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('planPrueba');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('plan-prueba-detail', {
            parent: 'entity',
            url: '/plan-prueba/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.planPrueba.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plan-prueba/plan-prueba-detail.html',
                    controller: 'PlanPruebaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('planPrueba');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PlanPrueba', function($stateParams, PlanPrueba) {
                    return PlanPrueba.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'plan-prueba',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('plan-prueba-detail.edit', {
            parent: 'plan-prueba-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plan-prueba/plan-prueba-dialog.html',
                    controller: 'PlanPruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PlanPrueba', function(PlanPrueba) {
                            return PlanPrueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plan-prueba.new', {
            parent: 'plan-prueba',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plan-prueba/plan-prueba-dialog.html',
                    controller: 'PlanPruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('plan-prueba', null, { reload: 'plan-prueba' });
                }, function() {
                    $state.go('plan-prueba');
                });
            }]
        })
        .state('plan-prueba.edit', {
            parent: 'plan-prueba',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plan-prueba/plan-prueba-dialog.html',
                    controller: 'PlanPruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PlanPrueba', function(PlanPrueba) {
                            return PlanPrueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plan-prueba', null, { reload: 'plan-prueba' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plan-prueba.delete', {
            parent: 'plan-prueba',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plan-prueba/plan-prueba-delete-dialog.html',
                    controller: 'PlanPruebaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PlanPrueba', function(PlanPrueba) {
                            return PlanPrueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plan-prueba', null, { reload: 'plan-prueba' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
