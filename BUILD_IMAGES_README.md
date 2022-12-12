# Bookinfo Sample

**Note**: We need the owner of the PR to perform the appropriate testing with built/pushed images to their own docker repository before we would build/push images to the official repository.

## Build docker images

```bash
build-images.sh <version> <prefix>
```

Where `<version>` is the tag and `<prefix>` is the docker registry to tag the images.

For example:

```bash
$ build-images.sh v0.1.0 docker.io/shamsher31
Sending build context to Docker daemon  1.218MB
Step 1/16 : FROM python:3.7.7-slim
3.7.7-slim: Pulling from library/python
8559a31e96f4: Pull complete
...
Successfully built 1b293582cc2e
Successfully tagged shamsher31/springcloud-bookinfo-ratings-v1:v0.1.0
Successfully tagged shamsher31/springcloud-bookinfo-ratings-v1:latest
```

## Push docker images to docker hub

After the local build is successful, you need to update the YAML file with the latest tag that you used during the build eg: `v0.1.0`.

Run the following script to build the docker images, push them to docker hub, and to update the YAML files in one step.

```bash
./build_push_update_images.sh <version> <prefix>
```

For example:

```bash
$ ./build_push_update_images.sh v0.1.0 --prefix=shamsher31
...
v0.1.0: digest: sha256:70634d3847a190b9826975c8 size: 3883
Pushing: shamsher31/springcloud-bookinfo-reviews-v2:v0.1.0
The push refers to a repository [docker.io/shamsher31/springcloud-bookinfo-reviews-v2]
...
```

Verify that expected tag eg: `v0.1.0` is updated in `platform/kube/bookinfo*.yaml` files.

## Tests
Test that the bookinfo samples work with the latest tag eg: `v0.1.0` that you pushed.

You need to enable springcloud on KSE and create a project (corresponding namespace) after it is successfully enabled, and then apply bookinfo.yaml under that namespace.
```bash
$ kubectl -n my-namespace apply -f platform/kube/bookinfo.yaml
deployment.apps/details-v1 created
...
```

To expose the productpage with NodePort for access outside the cluster, you need to apply productpage-nodeport.yaml
```bash
$ kubectl -n my-namespace apply -f platform/kube/productpage-nodeport.yaml
```

Then you can see MicroService, Configurations, Gateways on the KSE Spring Cloud page. 
You can also test it by hitting productpage in the browser.

```bash
http://IP:PRODUCTPAGE_NODEPORT
```

You should see the following in the browser.

![image-20220325114454233](docs/images/image-20220325114454233.png)

