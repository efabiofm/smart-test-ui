(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-alerta', {
            parent: 'entity',
            url: '/tipo-alerta',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.tipoAlerta.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-alerta/tipo-alertas.html',
                    controller: 'TipoAlertaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoAlerta');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-alerta-detail', {
            parent: 'entity',
            url: '/tipo-alerta/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.tipoAlerta.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-alerta/tipo-alerta-detail.html',
                    controller: 'TipoAlertaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoAlerta');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TipoAlerta', function($stateParams, TipoAlerta) {
                    return TipoAlerta.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-alerta',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-alerta-detail.edit', {
            parent: 'tipo-alerta-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-alerta/tipo-alerta-dialog.html',
                    controller: 'TipoAlertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoAlerta', function(TipoAlerta) {
                            return TipoAlerta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-alerta.new', {
            parent: 'tipo-alerta',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-alerta/tipo-alerta-dialog.html',
                    controller: 'TipoAlertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                metodo: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-alerta', null, { reload: 'tipo-alerta' });
                }, function() {
                    $state.go('tipo-alerta');
                });
            }]
        })
        .state('tipo-alerta.edit', {
            parent: 'tipo-alerta',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-alerta/tipo-alerta-dialog.html',
                    controller: 'TipoAlertaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoAlerta', function(TipoAlerta) {
                            return TipoAlerta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-alerta', null, { reload: 'tipo-alerta' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-alerta.delete', {
            parent: 'tipo-alerta',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-alerta/tipo-alerta-delete-dialog.html',
                    controller: 'TipoAlertaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoAlerta', function(TipoAlerta) {
                            return TipoAlerta.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-alerta', null, { reload: 'tipo-alerta' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
