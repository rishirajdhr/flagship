import * as Dialog from "@radix-ui/react-dialog";
import { Form, Link, redirect, useSearchParams } from "react-router";
import type { Route } from "./+types/projects-dashboard";
import { authContext } from "~/middleware-context";
import {
  createProject,
  getAllProjects,
  type NewProjectParams,
} from "~/api/projects";

type Project = {
  id: number;
  name: string;
  description: string;
  owner: string;
  createdAt: string;
  updatedAt: string;
};

export async function clientLoader({ context }: Route.ClientLoaderArgs) {
  const auth = context.get(authContext);
  if (auth === null) {
    throw redirect("/login");
  }

  const result = await getAllProjects(auth);

  if (result.status === 401) {
    throw redirect("/login");
  }

  if (!result.ok) {
    throw new Response("Failed to load projects", { status: result.status });
  }

  const projects = await result.json();
  return projects as Project[];
}

export async function clientAction({
  request,
  context,
}: Route.ClientActionArgs) {
  const auth = context.get(authContext);
  if (auth === null) {
    throw redirect("/login");
  }

  const formData = await request.formData();
  const name = formData.get("name")?.toString() ?? "";
  const description = formData.get("description")?.toString() ?? "";
  const newProjectParams: NewProjectParams = { name, description };

  const result = await createProject(newProjectParams, auth);

  if (result.status === 401) {
    throw redirect("/login");
  }

  if (!result.ok) {
    throw new Response("Failed to create project", { status: result.status });
  }

  const project = await result.json();
  return project as Project;
}

export default function ProjectsDashboard({
  loaderData: projects,
}: Route.ComponentProps) {
  const [searchParams, setSearchParams] = useSearchParams();
  const modal = searchParams.get("modal");

  const handleCreateProjectDialogOpenChange = (open: boolean) => {
    setSearchParams(
      (prevSearchParams) => {
        if (open) {
          prevSearchParams.set("modal", "create-project");
        } else {
          prevSearchParams.delete("modal");
        }
        return prevSearchParams;
      },
      { replace: true }
    );
  };

  return (
    <main className="p-8">
      <section className="mx-auto max-w-2xl space-y-8">
        <div className="flex flex-row items-center justify-between">
          <h1 className="text-4xl font-light tracking-tight text-gray-800">
            Projects
          </h1>

          <Dialog.Root
            open={modal === "create-project"}
            onOpenChange={handleCreateProjectDialogOpenChange}
          >
            <Dialog.Trigger asChild>
              <button className="flex cursor-pointer flex-row items-center justify-center gap-1 rounded-sm bg-orange-500 px-3 py-1.5 text-sm text-white shadow-xs transition-all hover:bg-orange-400 hover:shadow-sm active:bg-orange-600">
                <span>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 24 24"
                    strokeWidth={1.5}
                    stroke="currentColor"
                    className="size-4"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      d="M12 4.5v15m7.5-7.5h-15"
                    />
                  </svg>
                </span>
                <span className="tracking-tight">New Project</span>
              </button>
            </Dialog.Trigger>
            <Dialog.Portal>
              <Dialog.Overlay className="fixed top-0 right-0 bottom-0 left-0 grid place-items-center bg-gray-800/25 backdrop-blur-xs">
                <Dialog.Content className="rounded border-gray-300 bg-white shadow">
                  <Form method="POST" action={`/projects`}>
                    <div className="flex flex-row items-center justify-between border-b border-gray-300 p-4">
                      <div>
                        <Dialog.Title className="text-xl font-semibold tracking-tight text-gray-800">
                          Create Project
                        </Dialog.Title>
                      </div>
                      <div>
                        <Dialog.Close asChild>
                          <button className="flex size-8 items-center justify-center rounded-full hover:bg-gray-200/40">
                            <span className="text-gray-800">
                              <svg
                                xmlns="http://www.w3.org/2000/svg"
                                fill="none"
                                viewBox="0 0 24 24"
                                strokeWidth={1.5}
                                stroke="currentColor"
                                className="size-6"
                              >
                                <path
                                  strokeLinecap="round"
                                  strokeLinejoin="round"
                                  d="M6 18 18 6M6 6l12 12"
                                />
                              </svg>
                            </span>
                          </button>
                        </Dialog.Close>
                      </div>
                    </div>
                    <div className="flex flex-col gap-6 p-4">
                      <div className="flex flex-col gap-2">
                        <label
                          htmlFor="name"
                          className="tracking-tight text-gray-800"
                        >
                          Name
                        </label>
                        <input
                          id="name"
                          name="name"
                          type="text"
                          placeholder="Name of the project"
                          className="w-80 rounded border border-gray-300 px-3 py-1.5 text-sm tracking-tight text-gray-800"
                        />
                      </div>
                      <div className="flex flex-col gap-2">
                        <label
                          htmlFor="description"
                          className="tracking-tight text-gray-800"
                        >
                          Description
                        </label>
                        <textarea
                          id="description"
                          name="description"
                          placeholder="A description for the project"
                          className="w-80 rounded border border-gray-300 px-3 py-1.5 text-sm tracking-tight text-gray-800"
                        />
                      </div>
                    </div>
                    <div className="flex flex-row items-center justify-end gap-2 border-t border-gray-300 p-4">
                      <Dialog.Close asChild>
                        <button
                          type="button"
                          className="flex w-24 flex-row items-center justify-center gap-2 rounded px-4 py-2 font-medium tracking-tight text-orange-500 transition-colors not-disabled:hover:bg-orange-100/40 not-disabled:active:bg-orange-100/25 disabled:opacity-50"
                        >
                          <span>Cancel</span>
                        </button>
                      </Dialog.Close>
                      <button
                        type="submit"
                        // disabled={isSubmitting}
                        className="flex w-24 flex-row items-center justify-center gap-2 rounded bg-orange-500 px-4 py-2 font-medium tracking-tight text-white transition-colors not-disabled:hover:bg-orange-400 not-disabled:active:bg-orange-600 disabled:opacity-50"
                      >
                        {/* {isSubmitting ? (
                                <>
                                  <span>
                                    <svg
                                      xmlns="http://www.w3.org/2000/svg"
                                      viewBox="0 0 24 24"
                                      fill="none"
                                      stroke="currentColor"
                                      strokeWidth={2}
                                      strokeLinecap="round"
                                      strokeLinejoin="round"
                                      className="size-5 animate-spin"
                                    >
                                      <path d="M21 12a9 9 0 1 1-6.219-8.56" />
                                    </svg>
                                  </span>
                                  <span>Logging in...</span>
                                </>
                              ) : (
                                <span>Login</span>
                              )} */}
                        <span>Create</span>
                      </button>
                    </div>
                  </Form>
                </Dialog.Content>
              </Dialog.Overlay>
            </Dialog.Portal>
          </Dialog.Root>
        </div>
        <div className="flex flex-col gap-4">
          {projects.map((project) => (
            <div
              className="group relative flex w-full flex-row items-center gap-4 rounded-lg border border-gray-200 p-8 shadow-xs hover:cursor-pointer hover:shadow-sm"
              key={project.name}
            >
              <div className="flex size-12 items-center justify-center rounded-full bg-orange-500">
                <span className="flex items-center justify-center text-xl leading-none font-semibold text-white">
                  {project.name.slice(0, 1)}
                </span>
              </div>
              <div className="flex flex-1 flex-col gap-1">
                <h2 className="text-xl tracking-tight text-gray-800">
                  {project.name}
                </h2>
                <p className="text-sm tracking-tight text-gray-600">
                  {project.description}
                </p>
              </div>
              <div className="text-gray-600">
                <Link
                  className="after:absolute after:top-0 after:right-0 after:bottom-0 after:left-0"
                  to={`/projects/${project.id}`}
                >
                  <span className="inline-block -translate-x-2 transition-transform group-hover:translate-x-0">
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      fill="none"
                      viewBox="0 0 24 24"
                      strokeWidth={1.5}
                      stroke="currentColor"
                      className="size-6"
                    >
                      <path
                        strokeLinecap="round"
                        strokeLinejoin="round"
                        d="m8.25 4.5 7.5 7.5-7.5 7.5"
                      />
                    </svg>
                  </span>
                </Link>
              </div>
            </div>
          ))}
        </div>
      </section>
    </main>
  );
}
