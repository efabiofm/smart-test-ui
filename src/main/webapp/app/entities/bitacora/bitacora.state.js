(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('bitacora', {
            parent: 'entity',
            url: '/bitacora',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.bitacora.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bitacora/bitacoras.html',
                    controller: 'BitacoraController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bitacora');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('bitacora-detail', {
            parent: 'entity',
            url: '/bitacora/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.bitacora.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bitacora/bitacora-detail.html',
                    controller: 'BitacoraDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bitacora');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Bitacora', function($stateParams, Bitacora) {
                    return Bitacora.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bitacora',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bitacora-detail.edit', {
            parent: 'bitacora-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bitacora/bitacora-dialog.html',
                    controller: 'BitacoraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Bitacora', function(Bitacora) {
                            return Bitacora.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bitacora.new', {
            parent: 'bitacora',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bitacora/bitacora-dialog.html',
                    controller: 'BitacoraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fecha: null,
                                jhUserId: null,
                                descripcion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bitacora', null, { reload: 'bitacora' });
                }, function() {
                    $state.go('bitacora');
                });
            }]
        })
        .state('bitacora.edit', {
            parent: 'bitacora',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bitacora/bitacora-dialog.html',
                    controller: 'BitacoraDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Bitacora', function(Bitacora) {
                            return Bitacora.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bitacora', null, { reload: 'bitacora' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bitacora.delete', {
            parent: 'bitacora',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bitacora/bitacora-delete-dialog.html',
                    controller: 'BitacoraDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Bitacora', function(Bitacora) {
                            return Bitacora.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bitacora', null, { reload: 'bitacora' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
