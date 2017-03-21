(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-header', {
            parent: 'entity',
            url: '/tipo-header',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.tipoHeader.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-header/tipo-headers.html',
                    controller: 'TipoHeaderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoHeader');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-header-detail', {
            parent: 'entity',
            url: '/tipo-header/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.tipoHeader.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-header/tipo-header-detail.html',
                    controller: 'TipoHeaderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoHeader');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TipoHeader', function($stateParams, TipoHeader) {
                    return TipoHeader.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-header',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-header-detail.edit', {
            parent: 'tipo-header-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-header/tipo-header-dialog.html',
                    controller: 'TipoHeaderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoHeader', function(TipoHeader) {
                            return TipoHeader.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-header.new', {
            parent: 'tipo-header',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-header/tipo-header-dialog.html',
                    controller: 'TipoHeaderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-header', null, { reload: 'tipo-header' });
                }, function() {
                    $state.go('tipo-header');
                });
            }]
        })
        .state('tipo-header.edit', {
            parent: 'tipo-header',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-header/tipo-header-dialog.html',
                    controller: 'TipoHeaderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoHeader', function(TipoHeader) {
                            return TipoHeader.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-header', null, { reload: 'tipo-header' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-header.delete', {
            parent: 'tipo-header',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-header/tipo-header-delete-dialog.html',
                    controller: 'TipoHeaderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoHeader', function(TipoHeader) {
                            return TipoHeader.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-header', null, { reload: 'tipo-header' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
