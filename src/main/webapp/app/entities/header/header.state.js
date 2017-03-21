(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('header', {
            parent: 'entity',
            url: '/header',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.header.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/header/headers.html',
                    controller: 'HeaderController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('header');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('header-detail', {
            parent: 'entity',
            url: '/header/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.header.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/header/header-detail.html',
                    controller: 'HeaderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('header');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Header', function($stateParams, Header) {
                    return Header.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'header',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('header-detail.edit', {
            parent: 'header-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/header/header-dialog.html',
                    controller: 'HeaderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Header', function(Header) {
                            return Header.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('header.new', {
            parent: 'header',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/header/header-dialog.html',
                    controller: 'HeaderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                serviceGroupId: null,
                                token: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('header', null, { reload: 'header' });
                }, function() {
                    $state.go('header');
                });
            }]
        })
        .state('header.edit', {
            parent: 'header',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/header/header-dialog.html',
                    controller: 'HeaderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Header', function(Header) {
                            return Header.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('header', null, { reload: 'header' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('header.delete', {
            parent: 'header',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/header/header-delete-dialog.html',
                    controller: 'HeaderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Header', function(Header) {
                            return Header.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('header', null, { reload: 'header' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
