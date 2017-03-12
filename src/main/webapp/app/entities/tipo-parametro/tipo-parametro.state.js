(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-parametro', {
            parent: 'entity',
            url: '/tipo-parametro',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.tipoParametro.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-parametro/tipo-parametros.html',
                    controller: 'TipoParametroController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoParametro');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-parametro-detail', {
            parent: 'entity',
            url: '/tipo-parametro/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.tipoParametro.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-parametro/tipo-parametro-detail.html',
                    controller: 'TipoParametroDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoParametro');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TipoParametro', function($stateParams, TipoParametro) {
                    return TipoParametro.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-parametro',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-parametro-detail.edit', {
            parent: 'tipo-parametro-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-parametro/tipo-parametro-dialog.html',
                    controller: 'TipoParametroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoParametro', function(TipoParametro) {
                            return TipoParametro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-parametro.new', {
            parent: 'tipo-parametro',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-parametro/tipo-parametro-dialog.html',
                    controller: 'TipoParametroDialogController',
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
                    $state.go('tipo-parametro', null, { reload: 'tipo-parametro' });
                }, function() {
                    $state.go('tipo-parametro');
                });
            }]
        })
        .state('tipo-parametro.edit', {
            parent: 'tipo-parametro',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-parametro/tipo-parametro-dialog.html',
                    controller: 'TipoParametroDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoParametro', function(TipoParametro) {
                            return TipoParametro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-parametro', null, { reload: 'tipo-parametro' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-parametro.delete', {
            parent: 'tipo-parametro',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-parametro/tipo-parametro-delete-dialog.html',
                    controller: 'TipoParametroDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoParametro', function(TipoParametro) {
                            return TipoParametro.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-parametro', null, { reload: 'tipo-parametro' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
