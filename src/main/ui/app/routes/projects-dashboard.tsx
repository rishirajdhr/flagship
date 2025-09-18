import { Link, redirect } from "react-router";
import type { Route } from "./+types/projects-dashboard";
import { authContext } from "~/middleware-context";
import { getAllProjects } from "~/api/projects";

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

export default function ProjectsDashboard({
  loaderData: projects,
}: Route.ComponentProps) {
  return (
    <main className="p-8">
      <section className="mx-auto max-w-2xl space-y-8">
        <div className="flex flex-row items-center justify-between">
          <h1 className="text-4xl font-light tracking-tight text-gray-800">
            Projects
          </h1>
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
