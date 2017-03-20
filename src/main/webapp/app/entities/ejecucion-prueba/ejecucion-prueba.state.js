(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ejecucion-prueba', {
            parent: 'entity',
            url: '/ejecucion-prueba',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.ejecucionPrueba.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ejecucion-prueba/ejecucion-pruebas.html',
                    controller: 'EjecucionPruebaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ejecucionPrueba');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ejecucion-prueba-detail', {
            parent: 'entity',
            url: '/ejecucion-prueba/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.ejecucionPrueba.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ejecucion-prueba/ejecucion-prueba-detail.html',
                    controller: 'EjecucionPruebaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ejecucionPrueba');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EjecucionPrueba', function($stateParams, EjecucionPrueba) {
                    return EjecucionPrueba.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ejecucion-prueba',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ejecucion-prueba-detail.edit', {
            parent: 'ejecucion-prueba-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ejecucion-prueba/ejecucion-prueba-dialog.html',
                    controller: 'EjecucionPruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EjecucionPrueba', function(EjecucionPrueba) {
                            return EjecucionPrueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ejecucion-prueba.new', {
            parent: 'ejecucion-prueba',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ejecucion-prueba/ejecucion-prueba-dialog.html',
                    controller: 'EjecucionPruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fecha: null,
                                tiempoRespuesta: null,
                                resultado: null,
                                jhUserId: null,
                                body: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ejecucion-prueba', null, { reload: 'ejecucion-prueba' });
                }, function() {
                    $state.go('ejecucion-prueba');
                });
            }]
        })
        .state('ejecucion-prueba.edit', {
            parent: 'ejecucion-prueba',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ejecucion-prueba/ejecucion-prueba-dialog.html',
                    controller: 'EjecucionPruebaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EjecucionPrueba', function(EjecucionPrueba) {
                            return EjecucionPrueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ejecucion-prueba', null, { reload: 'ejecucion-prueba' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ejecucion-prueba.delete', {
            parent: 'ejecucion-prueba',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ejecucion-prueba/ejecucion-prueba-delete-dialog.html',
                    controller: 'EjecucionPruebaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EjecucionPrueba', function(EjecucionPrueba) {
                            return EjecucionPrueba.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ejecucion-prueba', null, { reload: 'ejecucion-prueba' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
